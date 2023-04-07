import { useEffect } from "react";

import styles from "./PerfumeItem.module.scss";
import { useNavigate } from "react-router-dom";

const PerfumeItem = (props) => {
  const navigate = useNavigate();
  useEffect(() => {
    const color = props.color;
    document.documentElement.style.setProperty("--color", `${color}`);
  }, [props.color]);

  const id = props.data !== undefined ? props.data.id : "";
  const imgURL = props.data !== undefined ? props.data.imgURL : "";
  const name = props.data !== undefined ? props.data.name : "";
  const brand = props.data !== undefined ? props.data.brand : "";

  const onPerfumeDetail = () => {
    navigate(`/perfume/${id}`);
  };

  return (
    <div className={styles.perfume_item} onClick={onPerfumeDetail}>
      <div className={styles.perfume_img_box}>
        <div className={styles.perfume_img}>
          <img src={imgURL} alt="perfume_img" />
        </div>
      </div>
      <p className={styles.perfume_name}>
        <b>{name}</b>
      </p>
      <p className={styles.perfume_brand}>{brand}</p>
    </div>
  );
};

export default PerfumeItem;
