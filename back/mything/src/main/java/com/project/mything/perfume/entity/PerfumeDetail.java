package com.project.mything.perfume.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //==비즈니스 로직==//
    /**
     * 조회수 증가
     */
    public Long updateViewCount() {
        this.viewCnt++;
        return this.viewCnt;
    }

    //리뷰 수정, 생성시 디테일 업데이트
    public void addSeason(String season) {
        if (season == "LIKE") {
            Long tempCnt = this.love + 1;
        } else if (season == "OK") {
            Long tempCnt = this.ok + 1;
        } else if (season == "HATE") {
            Long tempCnt = this.hate + 1;
        } else {
            throw new IllegalStateException("존재하지 않는 선호도 입니다");
        }
    }
}
