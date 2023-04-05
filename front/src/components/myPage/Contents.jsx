import { useState, useRef, useEffect } from "react";
import myPageImg from "../../asset/images/mypage_img.png";
import Servey from "./servey/Servey";
import Review from "./review/Review";

import styles from "./Contents.module.scss";

const Contents = () => {
  const [isSelected, setIsSelected] = useState(true);
  const topRef = useRef(null);

  let serveySelected = styles.list_selected;
  let reviewSelected = styles.list;

  if (isSelected) {
    serveySelected = styles.list_selected;
    reviewSelected = styles.list;
  } else {
    serveySelected = styles.list;
    reviewSelected = styles.list_selected;
  }

  const serveyResultClickHandler = () => {
    setIsSelected(true);
    topRef.current.scrollTop = 0;
  };

  const reviewListClickHandler = () => {
    setIsSelected(false);
    topRef.current.scrollTop = 0;
  };

  useEffect(() => {
    let vh = 0;
    vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }, []);

  return (
    <div className={styles.contents} ref={topRef}>
      <div className={styles.nav_bar}>
        <div className={styles.select_bar}>
          <div className={[serveySelected]} onClick={serveyResultClickHandler}>
            취향조사결과
          </div>
          <div className={[reviewSelected]} onClick={reviewListClickHandler}>
            향수리뷰등록
          </div>
        </div>
        <div className={styles.select_bar_img}>
          <img src={myPageImg} alt="mypage" />
        </div>
      </div>
      {isSelected ? <Servey /> : <Review />}
    </div>
  );
};

export default Contents;
