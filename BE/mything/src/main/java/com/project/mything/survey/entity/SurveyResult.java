package com.project.mything.survey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mything.member.entity.Member;
import com.project.mything.perfume.entity.Category;
import com.project.mything.perfume.entity.Perfume;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "survey_result")
public class SurveyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_result_id")
    private Long id;

    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "kakao_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "perfume_id")
    private Perfume perfume;

    public void addMember(Member member) {
        if (this.member != null) {
            this.member.getSurveyResultList().remove(this);
        }
        this.member = member;
        member.getSurveyResultList().add(this);
    }

    public void addPerfume(Perfume perfume) {
        if (this.perfume != null) {
            this.perfume.getSurveyResultList().remove(this);
        }
        this.perfume = perfume;
        perfume.getSurveyResultList().add(this);
    }
}
