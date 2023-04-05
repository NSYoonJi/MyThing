import styles from "./PerfumeItem.module.scss";

const PerfumeItem = (props) => {
  return (
    <div className={styles.perfume_item}>
      <div className={styles.perfume_img_box}>
        <div className={styles.perfume_img}>
          <img src={props.data.imgUrl} alt="perfume_img" />
        </div>
      </div>
      <p className={styles.perfume_name}>
        <b>{props.data.name}</b>
      </p>
      <p className={styles.perfume_brand}>{props.data.brand}</p>
      <p>{props.data.date}</p>
    </div>
  );
};

export default PerfumeItem;
