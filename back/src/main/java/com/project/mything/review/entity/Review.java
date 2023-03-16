package com.project.mything.review.entity;

import com.project.mything.perfume.entity.Longevity;
import com.project.mything.perfume.entity.Preference;
import com.project.mything.perfume.entity.Sillage;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @NotNull
    private String season;

    @Enumerated(EnumType.STRING)
    private Preference preference;

    @Enumerated(EnumType.STRING)
    private Longevity longevity;

    @Enumerated(EnumType.STRING)
    private Sillage sillage;

}
