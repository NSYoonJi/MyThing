package com.project.mything.member.repository;

import com.project.mything.member.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    // memberid 로 memberprofile 조회
    @Query(value = "select mf from MemberProfile mf where mf.member.id =:mid")
    public Optional<MemberProfile> findByMemberId(@Param("mid") Long id);


    public Optional<MemberProfile> findByNickname(String nickname);


    boolean existsByNickname(String nickname);
}
