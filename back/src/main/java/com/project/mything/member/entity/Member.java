package com.project.mything.member.entity;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    // 카카오 아이디
    @Id
    @Column(name = "kakao_id")
    private Long id;

    private String year;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
