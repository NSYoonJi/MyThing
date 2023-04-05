import SentimentSatisfiedAltIcon from "@mui/icons-material/SentimentSatisfiedAlt";
import SentimentNeutralIcon from "@mui/icons-material/SentimentNeutral";
import SentimentVeryDissatisfiedIcon from "@mui/icons-material/SentimentVeryDissatisfied";
import LocalFloristIcon from "@mui/icons-material/LocalFlorist";
import LightModeIcon from "@mui/icons-material/LightMode";
import EnergySavingsLeafIcon from "@mui/icons-material/EnergySavingsLeaf";
import AcUnitIcon from "@mui/icons-material/AcUnit";

import styles from "./PerfumeBox.module.scss";

const PerfumeBox = (props) => {
  const reviewCount = props.data.length !== 0 ? props.data.reviewList.length : 0;

  return (
    <div className={styles.perfume_box}>
      <div className={styles.perfume}>
        <div className={styles.perfume_image}>
          <img src={props.data.perfumeImgURL} alt="perfume" />
        </div>
        <div className={styles.perfume_desc}>
          <h1>{props.data.name}</h1>
          <h1>{props.data.brand}</h1>
          <h3>{reviewCount}개의 리뷰</h3>
          <h3>조회수 {props.data.viewCnt}회</h3>
        </div>
      </div>
      <div className={styles.review}>
        <div className={styles.review_box}>
          <div>
            <SentimentSatisfiedAltIcon />
            <p>좋아요</p>
            <p>{props.data.love}</p>
          </div>
          <div>
            <SentimentNeutralIcon />
            <p>보통</p>
            <p>{props.data.ok}</p>
          </div>
          <div>
            <SentimentVeryDissatisfiedIcon />
            <p>싫어요</p>
            <p>{props.data.hate}</p>
          </div>
          <div>
            <LocalFloristIcon />
            <p>봄</p>
            <p>{props.data.spring}</p>
          </div>
          <div>
            <LightModeIcon />
            <p>여름</p>
            <p>{props.data.summer}</p>
          </div>
          <div>
            <EnergySavingsLeafIcon />
            <p>가을</p>
            <p>{props.data.fall}</p>
          </div>
          <div>
            <AcUnitIcon />
            <p> 겨울</p>
            <p>{props.data.winter}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PerfumeBox;
