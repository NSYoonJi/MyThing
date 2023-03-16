package com.project.mything.perfume.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

}
