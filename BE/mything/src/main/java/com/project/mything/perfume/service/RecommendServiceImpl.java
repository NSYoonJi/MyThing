package com.project.mything.perfume.service;

import com.google.gson.Gson;
import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.MemberLike;
import com.project.mything.member.entity.MemberProfile;
import com.project.mything.member.repository.MemberProfileRepository;
import com.project.mything.member.repository.MemberRepository;
import com.project.mything.perfume.dto.*;
import com.project.mything.perfume.entity.Note;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.repository.NoteRepository;
import com.project.mything.perfume.repository.PerfumeRepository;
import com.project.mything.review.entity.Review;
import com.project.mything.review.repository.ReviewRepository;
import com.project.mything.survey.entity.SurveyResult;
import com.project.mything.survey.repository.SurveyResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService{

    private final PerfumeRepository perfumeRepository;
    private final SurveyResultRepository surveyResultRepository;
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final NoteRepository noteRepository;
    private final JwtService jwtService;
    private final ReviewRepository reviewRepository;


    /*
     *   클릭 기반 추천
     */
    @Override
    public List<RecommendPerfumesResponse> findPerfumeByClick(Long perfumeId) throws IOException {
        String url = "https://j8b207.p.ssafy.io/bigdata/recommend/survey/" + perfumeId + "/";
        Response execute = springToDjango(url);
        List<RecommendPerfumesResponse> recommendPerfumesResponse = recommend(execute);
        return recommendPerfumesResponse;
    }

    /*
     *   리뷰 기반 추천
     */
    @Cacheable(key = "#id", cacheNames = "recommendPerfumesResponse")
    @Override
    public List<RecommendPerfumesResponse> findPerfumeByReview(String token, Long id) throws IOException {
//        Long mid = jwtService.getUserIdFromToken(token);
        List<Review> reviews = reviewRepository.findByMemberId(id);
        if(reviews.size() == 0){
            List<RecommendPerfumesResponse> responseList = new ArrayList<>();
            responseList.add(
                    RecommendPerfumesResponse.builder()
                            .status("리뷰가 없습니다.")
                            .build()
            );
            return responseList;
        }

        Long perfumeId = reviews.get(reviews.size() - 1).getPerfume().getId();
        String url = "https://j8b207.p.ssafy.io/bigdata/recommend/recommend-review/" + perfumeId + "/";
        Response execute = springToDjango(url);
        String s = execute.body().toString();
        List<RecommendPerfumesResponse> recommendPerfumesResponse = recommend(execute);
        return recommendPerfumesResponse;
    }

    /*
     * 설문 기반 추천
     */

    @Override
    public List<RecommendPerfumesResponse> findPerfumeBySurvey(String token) throws IOException {
        Long mid = jwtService.getUserIdFromToken(token);

        List<SurveyResult> allByMemberId = surveyResultRepository.findAllByMember_Id(mid);
        if(!Optional.ofNullable(allByMemberId).isPresent()){
            List<RecommendPerfumesResponse> responseList = new ArrayList<>();
            responseList.add(
                    RecommendPerfumesResponse.builder()
                            .status("설문조사가 없습니다.")
                            .build()
            );
            return responseList;
        }
        String url = "https://j8b207.p.ssafy.io/bigdata/recommend/survey/" + allByMemberId.get(allByMemberId.size() - 1).getPerfume().getId() + "/";
        Response execute = springToDjango(url);
        List<RecommendPerfumesResponse> recommendPerfumesResponse = recommend(execute);
        return recommendPerfumesResponse;
    }

    /*
     *   설문 기반 반대 추천
     */
    @Override
    public List<RecommendPerfumesResponse> findPerfumeBySurveyReverse(String token) throws IOException {
        Long mid = jwtService.getUserIdFromToken(token);

        List<SurveyResult> allByMemberId = surveyResultRepository.findAllByMember_Id(mid);
        if(!Optional.ofNullable(allByMemberId).isPresent()){
            List<RecommendPerfumesResponse> responseList = new ArrayList<>();
            responseList.add(
                    RecommendPerfumesResponse.builder()
                            .status("설문조사가 없습니다.")
                            .build()
            );
            return responseList;
        }

        String url = "https://j8b207.p.ssafy.io/bigdata/recommend/survey-reverse/" + allByMemberId.get(allByMemberId.size() - 1).getPerfume().getId() + "/";
        Response execute = springToDjango(url);
        List<RecommendPerfumesResponse> recommendPerfumesResponse = recommend(execute);
        return recommendPerfumesResponse;
    }

    /*
     *   선호향 기반 추천
     */
    @Override
    public List<RecommendPerfumesResponse> findPerfumeByPrefer(PreferNotesRequest preferNotesRequest) throws IOException {
        HashMap<String, String[]> prefer = new HashMap<>();
        prefer.put("top_notes", preferNotesRequest.getTopNotes());
        prefer.put("middle_notes", preferNotesRequest.getMiddleNotes());
        prefer.put("base_notes", preferNotesRequest.getBaseNotes());

        Response execute = postSpringToDjango(prefer);
        List<RecommendPerfumesResponse> recommendPerfumesResponse = recommend(execute);
        return recommendPerfumesResponse;
    }

    /*
     *   유저의 선호향 기반 추천
     */
    @Override
    public List<RecommendPerfumesResponse> findUserPerfumeByPrefer(String token) throws IOException {
        Long mid = jwtService.getUserIdFromToken(token);
        Optional<MemberProfile> byMemberId = memberProfileRepository.findByMemberId(mid);

        if(!byMemberId.isPresent() || byMemberId.get().getPreferIncense().equals("")){
            List<RecommendPerfumesResponse> responseList = new ArrayList<>();
            responseList.add(
                    RecommendPerfumesResponse.builder()
                            .status("유저의 선호향이 없습니다.")
                            .build()
            );
            return responseList;
        }
//        String[] split = byMemberId.get().getPreferIncense().split(",");
        HashMap<String, String[]> prefer = new HashMap<>();
        prefer.put("top_notes", byMemberId.get().getPreferIncense().split(","));
        prefer.put("middle_notes", new String[] {"nan"});
        prefer.put("base_notes", new String[] {"nan"});

        Response execute = postSpringToDjango(prefer);
        List<RecommendPerfumesResponse> recommendPerfumesResponse = recommend(execute);
        return recommendPerfumesResponse;
    }

    /*
     *   유저가 취향 조사 할때 저장후 향수 하나 리턴
     */
    @Transactional
    @Override
    public RecommendPerfumesResponse findPerfumeByUserSurvey(String token, UserServeyRequest userServeyRequest) throws IOException {
        Long mid = jwtService.getUserIdFromToken(token);

        HashMap<String, String[]> prefer = new HashMap<>();
        prefer.put("top_notes", userServeyRequest.getTopNotes());
        prefer.put("middle_notes", userServeyRequest.getMiddleNotes());
        prefer.put("base_notes", userServeyRequest.getBaseNotes());

        Response execute = postSpringToDjango(prefer);
        List<RecommendPerfumesResponse> recommend = recommend(execute);

        surveyResultRepository.save(
                new SurveyResult().builder()
                        .time(LocalDateTime.now())
                        .member(memberRepository.findById(mid).get())
                        .perfume(perfumeRepository.findById(recommend.get(0).getId()).get())
                        .build()
        );

        return recommend.get(0);
    }

    /*
     *   게스트가 취향 조사 할때 저장후 향수 하나 리턴
     */
    @Transactional
    @Override
    public RecommendPerfumesResponse findPerfumeByGuestSurvey(UserServeyRequest userServeyRequest) throws IOException {
        HashMap<String, String[]> prefer = new HashMap<>();
        prefer.put("top_notes", userServeyRequest.getTopNotes());
        prefer.put("middle_notes", userServeyRequest.getMiddleNotes());
        prefer.put("base_notes", userServeyRequest.getBaseNotes());

        Response execute = postSpringToDjango(prefer);
        List<RecommendPerfumesResponse> recommend = recommend(execute);

        return recommend.get(0);
    }

    /*
     *   Django 통신 후 받은 추천 목록 Response로 변환
     */
    public List<RecommendPerfumesResponse> recommend(Response execute) throws IOException{
        // 추천 받은 향수 id
        String strToRecommends = execute.body().string();
        String[] arrToRecommends = strToRecommends.substring(1, strToRecommends.length() - 1).split(",");

        // 클릭 기반 추천받은 향수정보 리스트
        List<Perfume> perfumesByClick = perfumeRepository.findByIdIn(
                Arrays.stream(arrToRecommends)
                        .mapToLong(i -> Long.parseLong(i))
                        .boxed()
                        .collect(Collectors.toList())
        );

        List<RecommendPerfumesResponse> recommendPerfumesResponse = new ArrayList<>();
        for(Perfume perfume : perfumesByClick){
            recommendPerfumesResponse.add(
                    RecommendPerfumesResponse.builder()
                            .id(perfume.getId())
                            .name(perfume.getKoName())
                            .brand(perfume.getKoBrand())
                            .imgURL(perfume.getImgURL())
                            .status("성공")
                            .build()
            );
        }

        // 향수 id, 이름, 브랜드, img만 return
//        ModelMapper mapper = new ModelMapper();
////    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        List<RecommendPerfumesResponse> recommendPerfumesResponse =
//                perfumesByClick.stream()
//                        .map(i -> mapper.map(i, RecommendPerfumesResponse.class))
//                        .collect(Collectors.toList());
        return recommendPerfumesResponse;
    }

    /*
     * 카테고리 클릭시 해당 노트 출력
     */
    @Override
    public List<FindNotesByCategoryResponse> findNotesByCategory(String category) {
        Long[] notes = findNotes(category);
        List<Note> noteToList = noteRepository.findByIdIn(notes);
        ModelMapper mapper = new ModelMapper();
        List<FindNotesByCategoryResponse> findNotesByCategoryResponseList = noteToList.stream()
                .map(i -> mapper.map(i, FindNotesByCategoryResponse.class))
                .collect(Collectors.toList());
        return findNotesByCategoryResponseList;
    }

    /*
     *   Djano랑 통신
     */
    public Response springToDjango(String url) throws IOException{
        // Django 통신을 위한 설정
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url(url).build();
        Response execute = okHttpClient.newCall(build).execute();
        return execute;
    }

    /*
     * 취향 조사 결과 Django랑 통신
     */
    public Response postSpringToDjango(HashMap<String, String[]> prefer) throws IOException{
        String url = "https://j8b207.p.ssafy.io/bigdata/recommend/prefer/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(prefer);

        Request build = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        return okHttpClient.newCall(build).execute();
    }

    public Long[] findNotes(String category){
        HashMap<String, Long[]> categorys = new HashMap<>();
        categorys.put("CITRUS", new Long[] {75L, 79L, 313L, 79L, 77L, 78L, 82L, 17L, 80L, 3L, 85L});
        categorys.put("FRUITS, VEGETABLES AND NUTS", new Long[] {146L, 132L, 138L, 117L, 182L, 170L, 171L, 174L});
        categorys.put("FLOWERS", new Long[] {105L, 7L, 166L, 314L, 94L, 99L, 22L, 11L, 1L, 109L, 147L, 167L, 154L, 101L, 10L, 155L, 116L, 24L});
        categorys.put("WHITE FLOWERS", new Long[] {19L, 86L, 14L, 157L, 16L, 25L});
        categorys.put("GREENS, HERBS AND FOUGERES", new Long[] {119L, 1333L, 164L, 45L, 1112L, 318L, 142L, 160L, 49L, 52L, 96L, 127L});
        categorys.put("SPICES", new Long[] {158L, 63L, 65L, 61L, 64L, 62L, 59L, 158L, 91L, 55L, 321L, 73L, 74L});
        categorys.put("SWEETS AND GOURMAND SMELLS", new Long[] {183L, 181L});
        categorys.put("WOODS AND MOSSES", new Long[] {41L, 186L, 39L, 114L, 34L, 110L, 33L, 2L, 315L});
        categorys.put("RESINS AND BALSAMS", new Long[] {69L, 390L, 95L, 68L, 15L, 98L});
        categorys.put("MUSK, AMBER AND ANIMAL", new Long[] {54L, 524L, 104L, 156L, 4L});
        categorys.put("NATURAL AND SYNTHETIC, POPULAR AND WEIRD", new Long[] {165L});
        return categorys.get(category);
    }
}
