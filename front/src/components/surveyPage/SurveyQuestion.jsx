import React from "react";
import styles from "./SurveyQuestion.module.scss";
import { useRecoilValue } from "recoil";
import { survey } from "../../store/store";

const SurveyQuestion = () => {
  const question = useRecoilValue(survey);
  return (
    <div className={styles.survey_question}>
      <div className={styles.question_text}>{question.question}</div>
    </div>
  );
};

export default SurveyQuestion;
