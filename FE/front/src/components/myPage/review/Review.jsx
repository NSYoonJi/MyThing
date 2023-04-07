import ReviewItem from "./ReviewItem";
import AddReview from "./AddReivew";
import { useEffect, useState } from "react";
import { getUserReviews } from "../../../sevices/apis";

import styles from "./Review.module.scss";

const Review = () => {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const getReview = async () => {
      const res = await getUserReviews();
      setReviews(res.data.data);
    };
    getReview();
  }, [setReviews]);

  const renderingReview = () => {
    const result = [];

    for (let i = 0; i < reviews.length; i++) {
      result.push(<ReviewItem data={reviews[i]} key={i} />);
    }

    return result;
  };

  return (
    <>
      <p>가지고 있는 향수를 등록하고, 당신의 취향에 맞는향수를 추천받아요</p>
      <div className={styles.perfumes}>
        <AddReview />
        {renderingReview()}
      </div>
    </>
  );
};

export default Review;
