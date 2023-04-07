import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { survey } from "../../store/store";
import { useSetRecoilState } from "recoil";

import SurveyPage from "../../components/surveyPage/SurveyPage";
import { getSurveyQuestion } from "../../sevices/apis";

const Survey = () => {
  const { num } = useParams();
  const setSurveyQuestion = useSetRecoilState(survey);

  useEffect(() => {
    fetchSurveyQuestion(num);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [num]);

  const fetchSurveyQuestion = async (num) => {
    const res = await getSurveyQuestion(num);
    setSurveyQuestion(res.data);
  };

  return (
    <>
      <SurveyPage />
    </>
  );
};

export default Survey;
