import React, { useEffect } from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import { surveyResult } from "../../store/store";
import { postSurveyResult } from "../../sevices/apis";
import styles from "./SurveyResult.module.scss";

export default function SurveyResult() {
  const data = JSON.parse(localStorage.getItem("surveyResult"));
  const [surveyPerfume, setSurveyPerfume] = useRecoilState(surveyResult);
  const perfumeData = useRecoilValue(surveyResult);

  useEffect(() => {
    fetchSurveyResult();

    // 뒤로가기 버튼 막기
  }, []);

  const fetchSurveyResult = async () => {
    const res = await postSurveyResult(data);
    setSurveyPerfume(res.data);
  };

  return (
    <div className={styles.results}>
      <div className={styles.result}>
        <div className={styles.perfume__image_box}>
          <img className={styles.perfume__image} src={perfumeData.imgURL} alt="surveyResult" />
        </div>
        <div className={styles.textbox}>
          <div className={styles.texts}>
            <div className={styles.inner_text1}>당신의 취:향은</div>
          </div>
          <div className={styles.texts}>
            <div className={styles.inner_text2}>{perfumeData.brand}</div>
            <div className={styles.inner_text3}>{perfumeData.name}</div>
          </div>
        </div>
      </div>
      {/* if문 */}
      <div className={styles.result_login}>
        <div className={styles.surveytext}>다른 추천 향수를 보고 싶다면?</div>
        <div className={styles.surveybutton}>취:향 로그인</div>
      </div>
      <div className={styles.result_user}>
        <div className={styles.surveytext}>다른 추천 향수를 보고 싶다면?</div>
        <div className={styles.surveybutton}>취:향 바로가기</div>
      </div>
    </div>
  );
}
