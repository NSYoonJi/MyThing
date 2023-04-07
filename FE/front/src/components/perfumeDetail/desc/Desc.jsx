import styles from "./Desc.module.scss";

const Desc = (props) => {
  return (
    <div className={styles.description_box}>
      <h1>About</h1>
      <p>{props.data.info}</p>
    </div>
  );
};

export default Desc;
