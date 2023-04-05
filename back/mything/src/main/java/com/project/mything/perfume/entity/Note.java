package com.project.mything.perfume.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    private String name;

    private String image;

    @Column(name = "ko_name")
    private String koName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    // == 연관관계 편의 메서드 == //
    // JPA에서 쿼리문 안 보내도 바로 조회 가능하다.
    public void addCategory(Category category) {
        if(this.category!=null){
            this.category.getNoteList().remove(this);
        }
        this.category = category;
        category.getNoteList().add(this);
    }

//    @Builder
//    public Note(String name, String image, Category category){
//        this.name = name;
//        this.image = image;
//        this.addCategory(category);
//    }
}
