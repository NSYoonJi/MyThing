package com.project.mything.auth.Repository;

import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    public Optional<MemberProfile> findById(Long id);


    public Optional<MemberProfile> findByNickname(String nickname);
}
