package com.project.mything.perfume.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    private String name;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // == 연관관계 편의 메서드 == //
    // JPA에서 쿼리문 안 보내도 바로 조회 가능하다.
    public void SetCategory(Category category) {
        this.category = category;
        category.getNotes().add(this);
    }
}
