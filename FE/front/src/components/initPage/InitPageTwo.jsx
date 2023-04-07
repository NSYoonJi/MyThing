import React from "react";
import useScrollFadeIn from "../../hooks/useScrollFadeIn";
import init_img1_1 from "../../asset/images/init/init1-1.png";
import init_img1_2 from "../../asset/images/init/init1-2.png";

import styles from "./InitPageTwo.module.scss";

const InitPageTwo = () => {

  const animationLeftItem = useScrollFadeIn('left', 1, 0)

  return (
    <>
      <div className={styles.init_page_two}>
        <div className={styles.img_box}>
          <img src={init_img1_1} alt="init1_1" className={styles.init_img1} />
          <img src={init_img1_2} alt="init1_2" className={styles.init_img1} />
        </div>
        <div className={styles.text_box1} {...animationLeftItem}>
          <div>
            <div className={styles.init_text}>향수가</div>
            <div className={styles.init_text}>처음이라면</div>
          </div>

          <div className={styles.division_line}></div>

          <div>
            <div>
              <span className={`${styles.init_text} ${styles.init_bold_text} `}>취향 테스트</span>
              <span className={styles.init_text}>를 통해</span>
            </div>
            <div className={styles.init_text}>당신만의 향수를</div>
            <div className={styles.init_text}>찾아보세요</div>
          </div>          
        </div>
      </div>
    </>
  );
};

export default InitPageTwo;
