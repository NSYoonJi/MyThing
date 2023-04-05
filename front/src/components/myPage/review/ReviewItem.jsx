import noImage from "../../../asset/images/noImage.png";
import styles from "./ReviewItem.module.scss";

const ReviewItem = (props) => {
  const noImg = props.data.reviewImgUrl === null ? noImage : props.data.reviewImgUrl;
  let season = "";
  let preference = "";
  let tenacity = "";
  let diffusion = "";

  props.data.season.forEach((data) => {
    season = season + data;
  });

  season = season.replace("spring", "🌸");
  season = season.replace("summer", "🌞");
  season = season.replace("fall", "🍁");
  season = season.replace("winter", "⛄");

  switch (props.data.preference) {
    case "LIKE":
      preference = "좋아요";
      break;
    case "OK":
      preference = "괜찮아요";
      break;
    case "HATE":
      preference = "싫어요";
      break;
    default:
      break;
  }

  switch (props.data.longevity) {
    case "LONG":
      tenacity = "오래감";
      break;
    case "MIDDLE":
      tenacity = "중간감";
      break;
    case "SHORT":
      tenacity = "사라짐";
      break;
    default:
      break;
  }

  switch (props.data.sillage) {
    case "STRONG":
      diffusion = "강함";
      break;
    case "NORMAL":
      diffusion = "보통";
      break;
    case "WEAK":
      diffusion = "약함";
      break;
    default:
      break;
  }

  return (
    <div className={styles.perfume_item}>
      <div className={styles.perfume_img_box}>
        <div className={styles.perfume_img}>
          <div className={styles.front}>
            <img
              className={styles.front}
              src={props.data.perfumeImgUrl}
              alt={props.data.perfumeName}
            />
          </div>
          <div className={styles.back}>
            <img className={styles.back} src={noImg} alt={props.data.perfumeName} />
          </div>
        </div>
      </div>
      <p className={styles.perfume_name}>
        <b>{props.data.perfumeName}</b>
      </p>
      <p className={styles.perfume_brand}>{props.data.brand}</p>
      <p>계&nbsp;&nbsp;&nbsp;절 : {season}</p>
      <p>선호도 : {preference}</p>
      <p>지속력 : {tenacity}</p>
      <p>확산력 : {diffusion}</p>
    </div>
  );
};

export default ReviewItem;
