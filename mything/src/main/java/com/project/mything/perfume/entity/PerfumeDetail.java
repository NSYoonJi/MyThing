package com.project.mything.perfume.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "perfume_detail")
public class PerfumeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_detail_id")
    private Long id;

    @Column(name ="view_cnt")
    private Long viewCnt;

    private Long love;

    private Long ok;

    private Long hate;

    private Long spring;

    private Long summer;

    private Long fall;

    private Long winter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfume_id")
    @JsonIgnore
    private Perfume perfume;
}
