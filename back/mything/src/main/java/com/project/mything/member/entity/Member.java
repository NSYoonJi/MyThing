package com.project.mything.member.entity;

import com.project.mything.review.entity.Review;
import com.project.mything.survey.entity.SurveyResult;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "member")
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "kakao_id")
    private Long id;

    private String password; // 비밀번호
    @Enumerated(EnumType.STRING)
    private Role role;
    private String testId;
//    private String refreshToken; // 리프레시 토큰

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberProfile memberProfile;

    @OneToMany(mappedBy = "member")
    private List<SurveyResult> surveyResultList = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberLike memberLike;

    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<>();

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
//    public void passwordEncode(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(this.password);
//    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", testId='" + testId + '\'' +
                ", refreshToken=" + refreshToken +
                ", memberProfile=" + memberProfile +
                ", surveyResultList=" + surveyResultList +
                ", memberLike=" + memberLike +
                ", reviewList=" + reviewList +
                '}';
    }
}
