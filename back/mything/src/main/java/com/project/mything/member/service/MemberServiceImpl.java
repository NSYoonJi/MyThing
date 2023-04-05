package com.project.mything.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.dto.*;
import com.project.mything.member.entity.Gender;
import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.MemberProfile;
import com.project.mything.member.entity.RefreshToken;
import com.project.mything.member.repository.MemberProfileRepository;
import com.project.mything.member.repository.MemberRepository;
import com.project.mything.member.repository.RefreshTokenRepository;
import com.project.mything.perfume.entity.Note;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.repository.NoteRepository;
import com.project.mything.review.dto.CreateReviewResponse;
import com.project.mything.review.entity.Review;
import com.project.mything.review.entity.ReviewImage;
import com.project.mything.survey.entity.SurveyResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Autowired
    private AmazonS3 amazonS3;

    // 회원가입시 추가 정보
    @Override
    public String join(String token, SignUpRequestDto signUpRequestDto, MultipartFile image) throws Exception {

        // 닉네임 중복 확인 후
        if (memberProfileRepository.findByNickname(signUpRequestDto.getNickname()).isPresent())
            return "이미 존재하는 닉네임 입니다";

        Long mid = jwtService.getUserIdFromToken(token);

        Optional<Member> findMember = memberRepository.findById(mid);
        Member newMember = findMember.orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));
        Gender userGender = Gender.FEMALE;
        if (signUpRequestDto.getGender().equals("남")) {
            userGender = Gender.MALE;
        }

        String imagePath = null;
        if (image == null || image.isEmpty()) {
            // 이미지 파일이 존재하지 않을 때
            imagePath = "https://j8b207.p.ssafy.io/find-image/images/user_image/root.png";
        } else {
            // 이미지 파일이 존재할 때
            String s3FileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(image.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, image.getInputStream(), objMeta);
            amazonS3.setObjectAcl(bucket, s3FileName, CannedAccessControlList.PublicRead);
            imagePath = amazonS3.getUrl(bucket, s3FileName).toString();
        }


        // memberprofile 생성
        MemberProfile memberProfile = MemberProfile.builder()
                .image(imagePath)
                .year(signUpRequestDto.getDate())
                .preferIncense(signUpRequestDto.getPrefer_insence())
                .hateIncense(signUpRequestDto.getHate_insence())
                .nickname(signUpRequestDto.getNickname())
                .gender(userGender)
                .member(newMember).build();

        memberProfileRepository.save(memberProfile);
        // TODO/설문조사여부조사
        // 설문조사 여부 조사
//        if (!surveyResultRepository.findByMemberId(memberId).isPresent()) {
//            // 1. 설문조사 안했으면 안했다고 프론트에 알려주기
//            return "설문조사 경로";
//            // 2, 설문조사 했으면 설문조사 한것도 저장 !
//        }

        if (signUpRequestDto.getId() != null) {
            Perfume perfume = Perfume.builder()
                    .brand(signUpRequestDto.getBrand())
                    .name(signUpRequestDto.getName())
                    .imgURL(signUpRequestDto.getImgURL())
                    .id(signUpRequestDto.getId())
                    .build();

            SurveyResult surveyResult = new SurveyResult();
            surveyResult.addMember(newMember);
            surveyResult.addPerfume(perfume);

            newMember.authorizeUser();

            String refresh = jwtService.createRefreshToken();
            RefreshToken refreshToken = RefreshToken.builder()
                    .refreshToken(refresh)
                    .member(newMember)
                    .build();

            log.info("refreshToken : {}", refreshToken.toString());
            refreshTokenRepository.save(refreshToken);

            return "회원가입 성공";
        }

        return "/survey/1";
    }

    //=========== 회원정보 조회 =============
    @Override
    public InfoResponseDto findMember(String accessToken) {
        // 멤버 찾고 정보 조회해서 dto 에 정보 필요한 정보 담아서 return
        Long mid = jwtService.getUserIdFromToken(accessToken);
        Optional<Member> member = memberRepository.findById(mid);
        if (!member.isPresent()) {
            throw new IllegalStateException("존재하지 않는 회원입니다");
        }
        Member findMember = member.get();
        String nickname = findMember.getMemberProfile().getNickname();
        String image = findMember.getMemberProfile().getImage();
        String birth = findMember.getMemberProfile().getYear();
        String gender = String.valueOf(findMember.getMemberProfile().getGender());
        findMember.getMemberProfile().getPreferIncense();
        String preferences = findMember.getMemberProfile().getPreferIncense();
        List<String> preference = List.of(preferences.split(","));
        List<String> preferenceNames = new ArrayList<>();

        for (int i = 0; i < preference.size(); i++) {
            Long noteId = Long.parseLong(preference.get(i));
            Optional<Note> note = noteRepository.findById(noteId);
            preferenceNames.add(note.get().getName());
        }


        InfoResponseDto infoResponseDto = new InfoResponseDto(preferenceNames, nickname, image, birth, gender);
        return infoResponseDto;
    }

    //=========== 회원정보 수정 =============
    @Override
    @Transactional
    public Long updateMember(String token, InfoUpdateRequestDto infoUpdateRequestDto) {
        Long mid = jwtService.getUserIdFromToken(token);
        Optional<Member> member = memberRepository.findById(mid);
        if (!member.isPresent()) {
            throw new IllegalStateException("존재하지 않는 회원입니다");
        }

        MemberProfile findMemberProfile = member.get().getMemberProfile();
        // 성별
        if (infoUpdateRequestDto.getGender().equals("FEMALE")) {
            findMemberProfile.setGender(Gender.FEMALE);
        } else {
            findMemberProfile.setGender(Gender.MALE);
        }
        // 이미지
        findMemberProfile.setImage(infoUpdateRequestDto.getImagePath());
        // 닉네임 (중복 조회)
        if (memberProfileRepository.existsByNickname(infoUpdateRequestDto.getNickname())) {
            if (!memberProfileRepository.findByNickname(infoUpdateRequestDto.getNickname()).get().getMember().getId().equals(member.get().getId())) {
                throw new IllegalStateException("사용중인 닉네임 입니다 !");
            }
        }

        findMemberProfile.setNickname(infoUpdateRequestDto.getNickname());
        // 생년
        findMemberProfile.setYear(infoUpdateRequestDto.getBirth());

        return mid;

    }

    //=========== 향 수정 =============

    @Override
    @Transactional
    public Long updatePreference(String token, PreferenceRequestDto preferenceRequestDto) {
        Long mid = jwtService.getUserIdFromToken(token);
        Optional<Member> member = memberRepository.findById(mid);
        if (!member.isPresent()) {
            throw new IllegalStateException("존재하지 않는 회원입니다");
        }

        MemberProfile findMemberProfile = member.get().getMemberProfile();
        findMemberProfile.setPreferIncense(preferenceRequestDto.getPreferIncense());
        findMemberProfile.setHateIncense(preferenceRequestDto.getHateIncense());
        return mid;
    }


// ==========================================안 씀요 ============================================
    /*
    public void kakaoLogin(String code, HttpServletResponse response) throws ParseException {
        // 1. 인가 코드로 엑세스 토큰 요청
        String accessToken = getAccessToken(code);
        // 2. 엑세스 토큰으로 카카오 사용자 정보 가져오기
        Long kakaoId = getKakaoUserInfo(accessToken);
        //3. 회원가입
       // Member member = registerKakaoMember(kakaoId);
    }


    private Long getKakaoUserInfo(String accessToken) throws ParseException {
        // HttpBody 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        // HTTP 요청보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoUserInfoRequest,
                String.class
        );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            String kakaoId = String.valueOf(jsonObject.get("id"));
            System.out.println("kakaoId String" + kakaoId);
            long kakaoi = Long.parseLong(kakaoId);
            System.out.printf("kakaoId Long%d%n", kakaoi);
            return kakaoi;


    }

    private String getAccessToken(String code){

        // POST 방식으로 key =value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        // HttpBody 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9eec0e493dbf3db1f50e9930111e3715");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
        params.add("code", code);
        // HttpHeader 와 HttpBody 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        // Http 요청하기 - Post 방식으로
        System.out.println(kakaoTokenRequest.getHeaders());
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            String accessToken = (String) jsonObject.get("access_token");
            System.out.println("access_token: "+accessToken);
            System.out.println("refresh_token: " + jsonObject.get("refresh_token"));
            return accessToken;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

     */
}
