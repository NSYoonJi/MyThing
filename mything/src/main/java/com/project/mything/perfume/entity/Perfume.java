package com.project.mything.perfume.entity;

import com.project.mything.review.entity.Review;
import com.project.mything.survey.entity.SurveyResult;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "perfume")
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String info;
    private String topNote;
    private String middleNote;
    private String baseNote;

    @OneToMany(mappedBy = "perfume")
    private List<SurveyResult> surveyResultList = new ArrayList<>();

    @OneToMany(mappedBy = "perfume")
    private List<Review> reviewList = new ArrayList<>();

    @OneToOne(mappedBy = "perfume", fetch = FetchType.LAZY)
    private PerfumeDetail perfumeDetail;

}
