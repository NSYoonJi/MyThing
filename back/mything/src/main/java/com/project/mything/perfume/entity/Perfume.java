package com.project.mything.perfume.entity;

import com.project.mything.review.entity.Review;
import com.project.mything.survey.entity.SurveyResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "perfume")
@Builder
@AllArgsConstructor
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    private Long id;

    @Column(name = "ko_name")
    private String koName;

    @Column(name = "ko_description")
    private String koDescription;

    @Column(name = "ko_brand")
    private String koBrand;

    private String name;

    private String brand;

    @Lob
    private String info;

    // todo: 리스트 형태로 반환
    private String notes;

    @Lob
    @Column(name = "image_url")
    private String imgURL;

    @OneToMany(mappedBy = "perfume")
    private List<SurveyResult> surveyResultList = new ArrayList<>();

    @OneToMany(mappedBy = "perfume")
    private List<Review> reviewList = new ArrayList<>();

    @OneToOne(mappedBy = "perfume", fetch = FetchType.LAZY)
    private PerfumeDetail perfumeDetail;

}
