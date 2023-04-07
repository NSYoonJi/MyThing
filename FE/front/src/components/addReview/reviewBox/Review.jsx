import ReviewItem from "./ReviewItem";
import styles from "./Review.module.scss";
import { useEffect, useState } from "react";
const Review = (props) => {
  const [selected, setSelected] = useState([]);
  let title = props.data[1];
  let space = "";

  if (props.data[0] === 0) {
    space = <>&nbsp;&nbsp;&nbsp;</>;
  }

  const HandleOnChangeSelect = async (data, season) => {
    if (season === 0) {
      if (selected.indexOf(data) === -1) {
        setSelected([...selected, data]);
      } else {
        let list = [...selected];
        list.splice(list.indexOf(data), 1);
        setSelected(list);
      }
    } else {
      setSelected(data);
    }
  };

  useEffect(() => {
    props.change(props.data[1], selected);
  }, [selected, props]);

  const rendering = () => {
    let result = [];

    for (let i = 2; i < props.data.length; i++) {
      result = [
        ...result,
        <ReviewItem
          data={props.data[i]}
          season={props.data[0]}
          selected={selected}
          no={i - 2}
          key={i}
          change={HandleOnChangeSelect}
        />,
      ];
    }

    return result;
  };

  return (
    <div className={styles.review}>
      <p>
        <b>
          {title}
          {space}
        </b>
      </p>
      <div className={styles.review_select_box}>{rendering()}</div>
    </div>
  );
};

export default Review;
