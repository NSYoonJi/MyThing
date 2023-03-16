package com.project.mything.survey.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey_result")
public class SurveyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_result_id")
    private Long id;

    private LocalDateTime time;
}
