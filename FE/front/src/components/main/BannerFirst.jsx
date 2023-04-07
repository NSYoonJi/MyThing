import styles from "./BannerFirst.module.scss";
// import React, { useEffect, useState } from "react";
import main from "../../asset/images/main/main.png";
import { Link } from "react-router-dom";

export default function Banner() {
  // const [imageUrl, setImageUrl] = useState("");

  // useEffect(() => {
  //   setImageUrl(main);
  // }, []);

  // UI
  return (
    <header
      className={styles.banner}
      style={{
        backgroundImage: `url(${main})`,
        backgroundPosition: "top center",
        backgroundSize: "cover",
      }}
    >
      <div className={styles.banner__contents}>
        <div className={styles.banner__text}>당신의 취:향을</div>
        <div className={styles.banner__text}>찾아드릴게요</div>
        <div className={styles.banner__button}>
          <Link to="/recommend">
            <button className={styles.button}>나의 취:향 추천</button>
          </Link>
        </div>
      </div>
    </header>
  );
}
