import styles from "./Triangle.module.scss";

const Triangle = () => {
  return (
    <>
      <div className={styles.triangle_box}>
        <div className={styles.triangle}>
          <div className={styles.slice1}></div>
          <div className={styles.slice2}></div>
          <div className={styles.slice3}></div>
        </div>
      </div>
    </>
  );
};

export default Triangle;
