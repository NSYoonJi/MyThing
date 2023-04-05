package com.project.mything.member.repository;

import com.project.mything.member.entity.MemberLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.mything.perfume.repository
 * fileName       : MemberLikeRepository
 * author         : SSAFY
 * date           : 2023-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR         NOTE
 * -----------------------------------------------------------
 * 2023-03-28        SSAFY       최초 생성
 */
public interface MemberLikeRepository extends JpaRepository<MemberLike, Long> {
    MemberLike findByMember_Id(Long id);
}
