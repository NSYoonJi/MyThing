package com.project.mything.member.repository;

import com.project.mything.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // member id 로 refreshtoken 조회
    @Query(value = "select rt from RefreshToken rt where rt.member.id =:mid")
    Optional<RefreshToken> findByMemberId(@Param("mid") Long id);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);


}
