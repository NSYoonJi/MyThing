import PerfumeItem from "./perfumeItem/PerfumeItem";
import pinset from "../../asset/images/pinset.png";
import { useState } from "react";

import styles from "./Recommend.module.scss";

const Recommend = () => {
  const [color, setColor] = useState("#fffbea");
  const handleChange = (data) => {
    setColor(data);
  };

  const renderingPinset = () => {
    const result = [];
    for (let i = 0; i < 5; i++) {
      result.push(<img src={pinset} alt="pinset" key={i} />);
    }

    return result;
  };

  const renderingPreference = () => {
    const result = [];
    for (let i = 0; i < 5; i++) {
      result.push(<PerfumeItem color={color} key={i} />);
    }

    return result;
  };

  return (
    <>
      <div className={styles.top}>
        <div className={styles.bg}>
          <h1>xxx 기반 추천</h1>{" "}
        </div>
        <div className={styles.item_pinset}>{renderingPinset()}</div>
        <div className={styles.item}>{renderingPreference()}</div>
      </div>
      <div className={styles.container}>
        <div
          className={`${styles.slice} ${styles.slice1}`}
          onClick={() => {
            handleChange("#fffbea");
          }}
        ></div>
        <div
          className={`${styles.slice} ${styles.slice2}`}
          onClick={() => {
            handleChange("#f5ebe0");
          }}
        ></div>
        <div
          className={`${styles.slice} ${styles.slice3}`}
          onClick={() => {
            handleChange("#f0dbdb");
          }}
        ></div>
        <div
          className={`${styles.slice} ${styles.slice4}`}
          onClick={() => {
            handleChange("#ecbdbd");
          }}
        ></div>
      </div>
    </>
  );
};

export default Recommend;
