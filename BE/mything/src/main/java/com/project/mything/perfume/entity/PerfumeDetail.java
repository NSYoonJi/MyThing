package com.project.mything.perfume.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mything.review.entity.Longevity;
import com.project.mything.review.entity.Preference;
import com.project.mything.review.entity.Sillage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    /**
     * 리뷰 수정, 생성시 디테일 업데이트 mode = true 증가 false 감소
     */
    public void updateReviewDetail(List<String> season, Preference preference, Boolean mode) {
        long delta = mode ? 1L : -1L;
        for (String s : season) {
            switch (s) {
                case "spring":
                    this.spring += delta;
                    break;
                case "summer":
                    this.summer += delta;
                    break;
                case "fall":
                    this.fall += delta;
                    break;
                case "winter":
                    this.winter += delta;
                    break;
                default:
                    throw new IllegalStateException("유효한 계절이 아닙니다.");
            }
        }

        switch (preference) {
            case LIKE:
                this.ok += delta;
                break;
            case OK:
                this.love += delta;
                break;
            case HATE:
                this.hate += delta;
                break;
            default:
                throw new IllegalStateException("유효한 선호도가 아닙니다.");
        }
    }
}
