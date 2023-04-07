import React, { useEffect, useState } from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import { surveyResult } from "../../store/store";
import { postSurveyResult, postMemberSurveyResult, getUserInfo } from "../../sevices/apis";
import styles from "./SurveyResult.module.scss";
import { Link } from "react-router-dom";
import Loading from "../common/Loading";
import { mode } from "../../constants";

export default function SurveyResult() {
  const data = JSON.parse(localStorage.getItem("surveyResult"));
  const [surveyPerfume, setSurveyPerfume] = useRecoilState(surveyResult);
  const perfumeData = useRecoilValue(surveyResult);
  const [loading, setLoading] = useState(null); // 로딩
  const [nickName, setNickname] = useState(null);

  const fetchUserData = async () => {
    try {
      const res = await getUserInfo();
      setNickname(res.data);
      console.log("토큰이 있엉");
      fetchMemberSurveyResult();
      console.log(nickName)
    } catch(e) {

      console.log("토큰없엉");
      fetchSurveyResult();
      console.log(nickName)
      
    }
  };

  // 유저 정보 가져오기
  const token = sessionStorage.getItem("accessToken");

  useEffect(() => {
    fetchUserData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    // 로컬 스토리지에 취향조사 결과 향수 정보 저장
    localStorage.setItem("surveyResultPerfume", JSON.stringify(surveyPerfume));
  }, [surveyPerfume]);

  //비로그인 상태
  const fetchSurveyResult = async () => {
    const res = await postSurveyResult(data);
    setSurveyPerfume(res.data);
  };

  //로그인 상태
  const fetchMemberSurveyResult = async () => {
    const res = await postMemberSurveyResult(data);
    setSurveyPerfume(res.data);
  };

  // 로딩
  //스피너를 보여줄 결과페이지에서 api가 실행되기 전 스피너의 모습이 보이게하고
  // api가 실행되고 난 후에는 스피너가 보이지 않게 만들어준다
  useEffect(() => {
    setLoading(true); // 스피너

    const timer = setTimeout(() => {
      setLoading(false); // 5초 뒤에 스피너 숨김
    }, 1300);

    return () => clearTimeout(timer); // 컴포넌트가 언마운트될 때 타이머 정리
  }, []);

  //UI
  return (
    <>
      {loading && <Loading />}
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
        {nickName ? (
          <div className={styles.result_user}>
            <div className={styles.surveytext}>다른 추천 향수를 보고 싶다면?</div>
            <Link to="/mainpage" style={{ textDecoration: "none" }}>
              <div className={styles.surveybutton}>취:향 바로가기</div>
            </Link>
          </div>
        ) : (
          <div className={styles.result_login}>
            <div className={styles.surveytext}>다른 추천 향수를 보고 싶다면?</div>
            <div className={styles.surveybutton}>
              {mode === "local" && (
                <a
                  href={`http://j8b207.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=http://localhost:3000/oauth/redirect`}
                >
                  취:향 로그인
                </a>
              )}
              {mode === "server" && (
                <a
                  href={`http://j8b207.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=http://j8b207.p.ssafy.io/oauth/redirect`}
                >
                  취:향 로그인
                </a>
              )}
            </div>
          </div>
        )}
      </div>
    </>
  );
}
