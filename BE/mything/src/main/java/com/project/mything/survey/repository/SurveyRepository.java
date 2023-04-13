package com.project.mything.survey.repository;


import com.project.mything.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 여기는 설문조사 이미지가 있는 repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {


    // 이름이 pNum-% 인 survey 모두 찾기
    @Query(value = "select s from Survey s where s.name like :pNum%")
    List<Survey> findByName(@Param("pNum") String pageNum);
}
