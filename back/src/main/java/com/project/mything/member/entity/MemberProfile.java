package com.project.mything.member.entity;

import javax.persistence.*;

@Entity
@Table(name = "member_profile")
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_profile_id")
    private Long id;

    @Column(name = "prefer_incense")
    private String preferIncense;

    @Column(name = "hate_incense")
    private String hateIncense;

    private String nickname;

    private String image;
}
