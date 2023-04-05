import profileImg from "../../../../asset/images/profile.svg";
import styles from "./Review.module.scss";
const Review = (props) => {
  let season = props.data.season;
  let preference = "";
  let tenacity = "";
  let diffusion = "";

  console.log(props.data);

  season = season.replace("spring", "🌸 ");
  season = season.replace("summer", "🌞 ");
  season = season.replace("fall", "🍁 ");
  season = season.replace("winter", "⛄ ");
  season = season.replace(",", " ");

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
    <>
      <div className={styles.review}>
        <div className={styles.review_item}>
          <p> {season}</p>
          <p>선호도 : {preference}</p>
          <p>지속력 : {tenacity}</p>
          <p>확산력 : {diffusion}</p>
        </div>
        <div className={styles.slice1}></div>
        <div className={styles.profile_box}>
          <div className={styles.profile_image}>
            <img src={profileImg} alt="profile_img" />
          </div>
          <div className={styles.profile}>
            <h2>{props.data.nickname}</h2>
          </div>
        </div>
      </div>
    </>
  );
};

export default Review;
