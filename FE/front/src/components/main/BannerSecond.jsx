import React from "react";
import banner2 from "../../asset/images/main/banner2.png";
import styles from "./BannerSecond.module.scss";
import { Link } from "react-router-dom";

export default function BannerSecond() {
  // UI
  return (
    <header className={styles.banner}>
      <div className={styles.rightside}>
        <div className={styles.textbox1}>
          <div className={styles.box}>
            <div className={styles.textStyle__big}>당신만을 위한</div>
            <div className={styles.textStyle__big}>향수 추천</div>
          </div>
          <div className={styles.box}>
            <Link to="/survey/1" style={{ textDecoration: "none" }}>
              <div className={styles.textStyle__small}>알아보기</div>
            </Link>
            <div className={styles.divisionline_big}></div>
          </div>
        </div>
        <div>
          <img className={styles.photobox} src={banner2} alt="배너 이미지" />
        </div>
        <div className={styles.textbox2}>
          <div className={styles.box}>
            <div className={styles.textStyle__big}>향수는</div>
            <div className={styles.textStyle__big}>처음이라</div>
          </div>
          <div className={styles.box}>
            <Link to="/guide" style={{ textDecoration: "none" }}>
              <div className={styles.textStyle__small}>가이드 보러가기</div>
              <div className={styles.divisionline_small}></div>
            </Link>
          </div>
        </div>
      </div>
    </header>
  );
}
