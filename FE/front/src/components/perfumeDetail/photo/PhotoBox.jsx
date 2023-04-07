import noImage from "../../../asset/images/noImage.png";

import styles from "./PhotoBox.module.scss";
import Photo from "./photo/Photo";

const PhotoBox = (props) => {
  const reviewImages = props.data.length !== 0 ? props.data.reviewImageList : [];

  const render = () => {
    const result = [];

    if (reviewImages.length === 0) {
      result.push(
        <img
          style={{ transform: "rotateY(180deg)" }}
          src={noImage}
          alt="review"
          key={Math.random()}
        />
      );
    } else {
      for (let i = 0; i < reviewImages.length; i++) {
        if (i > 8) {
          break;
        }
        result.push(<Photo data={reviewImages[i]} key={i} />);
      }
    }

    return result;
  };

  return (
    <div className={styles.photo_box}>
      <h1>Photo</h1>
      <div className={styles.image_box}>{render()}</div>
    </div>
  );
};

export default PhotoBox;
