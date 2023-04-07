import { Link, useNavigate } from "react-router-dom";
import { postUserReivew } from "../../../sevices/apis";
import Button from "../../common/button/Button";
import Review from "./Review";

import styles from "./ReviewBox.module.scss";
import { addReviewImage, addReviewPerfume } from "../../../store/store";
import { useRecoilValue } from "recoil";

const ReviewBox = (props) => {
  const navigate = useNavigate();
  const perfumeInfo = useRecoilValue(addReviewPerfume);
  const perfumeImage = useRecoilValue(addReviewImage);
  const type = props.type === "modify" ? "수정" : "등록";

  const list = [
    [0, "계절", "🌿", "🏄‍♂️", "🍁", "⛄"],
    [1, "선호도", "좋아요", "괜찮아요", "싫어요"],
    [1, "지속력", "오래감", "중간감", "사라짐"],
    [1, "확산력", "강함", "보통", "약함"],
  ];

  const reviewList = [];

  const handleChange = (no, review) => {
    reviewList[no] = review;
  };

  const handleAddReview = () => {
    const data = calc();

    const formData = new FormData();

    formData.append(
      "request",
      new Blob([JSON.stringify(data)], {
        type: "application/json",
      })
    );
    
    if (perfumeImage) {
      formData.append("image", perfumeImage);
    }

    postUserReivew(formData);
    navigate("/mypage");
  };

  const rendering = () => {
    let result = [];

    for (let i = 0; i < list.length; i++) {
      result = [...result, <Review data={list[i]} key={i} change={handleChange} />];
    }
    return result;
  };

  const calc = () => {
    let season = [];
    let preference = "";
    let longevity = "";
    let sillage = "";

    for (let i = 0; i < reviewList["계절"].length; i++) {
      switch (reviewList["계절"][i]) {
        case 0:
          season[i] = "spring";
          break;
        case 1:
          season[i] = "summer";
          break;
        case 2:
          season[i] = "fall";
          break;
        case 3:
          season[i] = "winter";
          break;
        default:
          break;
      }
    }

    switch (reviewList["선호도"]) {
      case 0:
        preference = "LIKE";
        break;
      case 1:
        preference = "OK";
        break;
      case 2:
        preference = "HATE";
        break;
      default:
        break;
    }

    switch (reviewList["지속력"]) {
      case 0:
        longevity = "LONG";
        break;
      case 1:
        longevity = "MIDDLE";
        break;
      case 2:
        longevity = "SHORT";
        break;
      default:
        break;
    }

    switch (reviewList["확산력"]) {
      case 0:
        sillage = "STRONG";
        break;
      case 1:
        sillage = "NORMAL";
        break;
      case 2:
        sillage = "WEAK";
        break;
      default:
        break;
    }

    return {
      perfumeId: perfumeInfo.id,
      season: season,
      preference: preference,
      longevity: longevity,
      sillage: sillage,
    };
  };

  return (
    <div className={styles.review_box}>
      <p>리뷰 등록</p>
      <div className={styles.review_list}>{rendering()}</div>
      <div className={styles.button_box}>
        <Button name={type} onClick={handleAddReview} />
        <Link to="/mypage">
          <Button name={"취소"} />
        </Link>
      </div>
    </div>
  );
};

export default ReviewBox;
