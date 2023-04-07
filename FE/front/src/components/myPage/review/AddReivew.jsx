import { Link } from "react-router-dom";
import styles from "./AddReview.module.scss";

const AddReview = () => {
  return (
    <div className={styles.perfume_item}>
      <Link to={"/mypage/addreview"} style={{ textDecoration: "none", color: "black" }}>
        <div className={styles.background}>
          <div className={styles.perfume_img_box}>
            <div className={styles.perfume_img}></div>
          </div>
          <p className={styles.perfume_name}>
            당신이 가지고 있는
            <br />
            취:향을
            <br />
            들려주세요
          </p>
        </div>
      </Link>
    </div>
  );
};

export default AddReview;
