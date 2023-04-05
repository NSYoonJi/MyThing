import React from "react";
import { useRecoilValue } from "recoil";
import { survey } from "../../store/store";
import { useLocation, useParams, useNavigate, json } from "react-router-dom";
import useScrollFadeIn from "../../hooks/useScrollFadeIn";
import SurveyImage from "./SurveyImage";

import styles from "./SurveyImages.module.scss";

const SurveyImages = () => {
  const num = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const surveydata = useRecoilValue(survey);
  const animationUpItem = useScrollFadeIn("up", 1, 0);

  const selectNote = (questionNum, answer) => {
    const noteList = [
      ["22", "157", "11"], // 플로럴
      ["77", "706", "370"], // 프레쉬
      ["691", "326", "41"], // 우디
      ["165", "4", "348"], // 오리엔탈
    ];
    return noteList[parseInt(answer)][questionNum];
  };

  const nextQeustion = (value) => {
    const searchParams = new URLSearchParams(location.search);

    if (num.num !== "4") {
      searchParams.set(`q${num.num}`, value);
      navigate(`/survey/${parseInt(num.num) + 1}?${searchParams.toString()}`);
    } else {
      const data = {
        topNotes: [selectNote(0, searchParams.get("q1") - 1)],
        middleNotes: [selectNote(1, searchParams.get("q2") - 1)],
        baseNotes: [selectNote(2, searchParams.get("q3") - 1)],
        // season: searchParams.get("q4"),
      };
      localStorage.setItem("surveyResult", JSON.stringify(data));
      navigate("/survey/result");
    }
  };

  return (
    <>
      <div className={styles.survey_images} {...animationUpItem}>
        <div onClick={() => nextQeustion(1)}>
          <SurveyImage answer={surveydata.answerA} image={surveydata.images[`${num.num}-a`]} />
        </div>
        <div onClick={() => nextQeustion(2)}>
          <SurveyImage answer={surveydata.answerB} image={surveydata.images[`${num.num}-b`]} />
        </div>
        <div onClick={() => nextQeustion(3)}>
          <SurveyImage answer={surveydata.answerC} image={surveydata.images[`${num.num}-c`]} />
        </div>
        <div onClick={() => nextQeustion(4)}>
          <SurveyImage answer={surveydata.answerD} image={surveydata.images[`${num.num}-d`]} />
        </div>
      </div>
    </>
  );
};

export default SurveyImages;
