import noImage from "../../../asset/images/noImage.png";

import styles from "./PhotoBox.module.scss";

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
        result.push(<img src={reviewImages[i].imgURL} alt="review" key={i} />);
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
