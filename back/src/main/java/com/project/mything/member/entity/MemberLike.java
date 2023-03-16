package com.project.mything.member.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_like")
public class MemberLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_like_id")
    private Long id;

    @Column(name = "member_like_image")
    private String likeImage;

    private LocalDateTime time;
}
