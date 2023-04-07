import styles from "./Button.module.scss";

const Button = (props) => {
  return (
    <button className={styles.button} onClick={props.onClick}>
      {props.name}
    </button>
  );
};

export default Button;
