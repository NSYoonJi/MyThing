import styles from "./ReviewItem.module.scss";
import { useEffect, useState } from "react";

const ReviewItem = (props) => {
  const [selected, setSelected] = useState("");
  let style = "";

  if (props.season === 0) {
    style = styles.season;
  } else {
    style = styles.other;
  }

  useEffect(() => {
    if (props.season === 1) {
      if (props.no === props.selected) {
        setSelected(styles.selected);
      } else {
        setSelected("");
      }
    }
  }, [props.no, props.selected, props.season]);

  const onSelectHandler = () => {
    if (props.season === 0) {
      if (selected === "") {
        setSelected(styles.selected);
      } else {
        setSelected("");
      }
    }
    props.change(props.no, props.season);
  };

  return (
    <>
      <div className={`${style} ${selected}`} key={props.data} onClick={onSelectHandler}>
        <p>{props.data}</p>
      </div>
    </>
  );
};

export default ReviewItem;
