package com.project.mything.survey.repository;

import com.project.mything.member.entity.MemberProfile;
import com.project.mything.survey.entity.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
    // memberid 로 surveyresult 조회
    @Query(value = "select sr from SurveyResult sr where sr.member.id =:memberId")
    Optional <SurveyResult> findByMemberId(@Param("memberId") Long id);

    List<SurveyResult> findAllByMember_Id(Long id);


}
