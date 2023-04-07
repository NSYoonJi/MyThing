import { useNavigate } from "react-router-dom";

import styles from "./PerfumeItem.module.scss";

const PerfumeItem = (props) => {
  const date = new Date(props.data.date);
  const navigate = useNavigate();

  const onPerfumeDetail = () => {
    navigate(`/perfume/${props.data.perfumeId}`);
  };

  return (
    <div className={styles.perfume_item} onClick={onPerfumeDetail}>
      <div className={styles.perfume_img_box}>
        <div className={styles.perfume_img}>
          <img src={props.data.imgUrl} alt="perfume_img" />
        </div>
      </div>
      <p className={styles.perfume_name}>
        <b>{props.data.name}</b>
      </p>
      <p className={styles.perfume_brand}>{props.data.brand}</p>
      <p>{date.toLocaleDateString("ko-KR")}</p>
    </div>
  );
};

export default PerfumeItem;
