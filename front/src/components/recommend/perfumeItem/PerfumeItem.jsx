import { useEffect } from "react";
import perfume_img from "../../../asset/images/perfume_img.png";

import styles from "./PerfumeItem.module.scss";

const PerfumeItem = (props) => {
  useEffect(() => {
    const color = props.color;
    document.documentElement.style.setProperty("--color", `${color}`);
  }, [props.color]);

  return (
    <div className={styles.perfume_item}>
      <div className={styles.perfume_img_box}>
        <div className={styles.perfume_img}>
          <img src={perfume_img} alt="perfume_img" />
        </div>
      </div>
      <p className={styles.perfume_name}>
        <b>오드 퍼퓸 탐다오</b>
      </p>
      <p className={styles.perfume_brand}>딥디크</p>
      <p>2023. 03. 08</p>
    </div>
  );
};

export default PerfumeItem;
