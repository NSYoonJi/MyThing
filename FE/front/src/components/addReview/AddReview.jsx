import { useEffect } from "react";
import styles from "./AddReview.module.scss";
import ImageBox from "./imageBox/ImageBox";
import ReviewBox from "./reviewBox/ReviewBox";

const AddReview = () => {
  useEffect(() => {
    let vh = 0;
    vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }, []);

  return (
    <>
      <div className={styles.bg}></div>
      <div className={styles.top}>
        <ImageBox />
        <ReviewBox />
      </div>
    </>
  );
};

export default AddReview;
