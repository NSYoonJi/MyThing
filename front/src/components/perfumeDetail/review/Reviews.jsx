import Review from "./review/Review";
import styles from "./Reviews.module.scss";
const Reviews = (props) => {
  const reviewList = props.data.length !== 0 ? props.data.reviewList : [];

  const render = () => {
    const result = [];
    if (reviewList.length === 0) {
      result.push(<h1 key={Math.random()}>등록된 리뷰가 없습니다.</h1>);
    } else {
      for (let i = 0; i < reviewList.length; i++) {
        result.push(<Review data={reviewList[i]} key={i} />);
      }
    }
    return result;
  };
  return (
    <div className={styles.review_box}>
      <h1>Reviews</h1>
      <div className={styles.reviews}>{render()}</div>
    </div>
  );
};

export default Reviews;
