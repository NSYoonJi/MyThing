package com.project.mything.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.dto.InfoResponseDto;
import com.project.mything.member.dto.InfoUpdateRequestDto;
import com.project.mything.member.dto.PreferenceRequestDto;
import com.project.mything.member.dto.SignUpRequestDto;
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
import com.project.mything.review.entity.ReviewImage;
import com.project.mything.survey.entity.SurveyResult;
import com.project.mything.survey.repository.SurveyResultRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final SurveyResultRepository surveyResultRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public Boolean findNickname(String nickname) {
        if (memberProfileRepository.findByNickname(nickname).isPresent())
            return false;
        return true;
    }

    // 회원가입시 추가 정보
    @Override
    public String join(String token, SignUpRequestDto signUpRequestDto, MultipartFile image) throws Exception {
//        // 닉네임 중복 확인 후
//        if (memberProfileRepository.findByNickname(signUpRequestDto.getNickname()).isPresent())
//            return "이미 존재하는 닉네임 입니다";

        Long mid = jwtService.getUserIdFromToken(token);

        Optional<Member> findMember = memberRepository.findById(mid);
        Member newMember = findMember.orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));
        Gender userGender = Gender.FEMALE;
        if (signUpRequestDto.getGender().equals("FEMALE")) {
            userGender = Gender.MALE;
        }

        // 기본 프로필
        String imagePath = "https://j8b207.p.ssafy.io/find-image/images/user_image/root.png";
        if (image != null && !image.isEmpty()) {
            // 등록한 프로필 이미지가 존재할 때
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
        log.info("확인 : {}", signUpRequestDto);

        if (signUpRequestDto.getId() != null) {
            Perfume perfume = Perfume.builder()
                    .brand(signUpRequestDto.getBrand())
                    .name(signUpRequestDto.getName())
                    .imgURL(signUpRequestDto.getImgURL())
                    .id(signUpRequestDto.getId())
                    .build();
            log.info("향수 : {}", perfume);
            SurveyResult surveyResult = SurveyResult.builder()
                    .time(LocalDateTime.now())
                    .member(newMember)
                    .perfume(perfume)
                    .build();

            surveyResultRepository.save(surveyResult);

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
            preferenceNames.add(note.get().getKoName());
        }


        InfoResponseDto infoResponseDto = new InfoResponseDto(preferenceNames, nickname, image,gender ,birth );
        return infoResponseDto;
    }

    //=========== 회원정보 수정 =============
    @Override
    @Transactional
    public Long updateMember(String token, InfoUpdateRequestDto infoUpdateRequestDto, MultipartFile image) throws IOException {
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
        if (!(image == null || image.isEmpty())) {
            String s3FileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(image.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, image.getInputStream(), objMeta);
            amazonS3.setObjectAcl(bucket, s3FileName, CannedAccessControlList.PublicRead);
            findMemberProfile.setImage(amazonS3.getUrl(bucket, s3FileName).toString());
        }
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
}
