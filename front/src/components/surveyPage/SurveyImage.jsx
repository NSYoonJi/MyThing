import React from "react";

import styles from "./SurveyImage.module.scss";

const SurveyImage = ({ answer, image }) => {
  return (
    <div className={styles.survey_image}>
      <img src={`${image}`} alt="img" className={styles.question_img} />
      <div className={styles.text_box}>
        <div className={styles.detail_text}>{answer[0]}</div>
        <div className={styles.title_text}>{answer[1]}</div>
      </div>
    </div>
  );
};

export default SurveyImage;
