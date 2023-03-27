package com.project.mything.member.entity;

import com.project.mything.review.entity.Review;
import com.project.mything.survey.entity.SurveyResult;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {

    // 카카오 아이디
    @Id
    @Column(name = "kakao_id")
    private Long id;

    private String year;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy ="member", fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberProfile memberProfile;

    @OneToMany(mappedBy = "member")
    private List<SurveyResult> surveyResultList = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberLike memberLike;

    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<>();


}
