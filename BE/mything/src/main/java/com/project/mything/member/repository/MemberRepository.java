package com.project.mything.member.repository;

import com.project.mything.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 정보 제공을 동의한 순간 DB에 저장해야하지만, 아직 추가 정보를 입력받지 않았으므로
     * 유저 객체는 DB에 있지만, 추가 정보가 빠진 상태이다.
     * 따라서 추가 정보를 입력받아 회원 가입을 진행할 때 식별자로 해당 회원을 찾기 위한 메소드
     */
    Optional<Member> findByTestId(String id);

    Optional<Member> findById(Long id);

}
