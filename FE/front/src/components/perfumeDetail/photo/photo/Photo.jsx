import FavoriteIcon from "@mui/icons-material/Favorite";
import PhotoModal from "./PhotoModal";

import styles from "./Photo.module.scss";
import { useState } from "react";
const Photo = (props) => {
  const [isDetail, setIsDetail] = useState(null);
  const handleDetail = () => {
    setIsDetail(props);
  };

  const handleModalClose = () => {
    setIsDetail(null);
  };

  return (
    <>
      {isDetail && <PhotoModal data={props} onConfirm={handleModalClose} />}
      <div className={styles.image} onClick={handleDetail}>
        <img src={props.data.imgURL} alt="review" />
        <div className={styles.like_box}>
          <FavoriteIcon className={styles.like} />
          {props.data.likeCnt}
        </div>
      </div>
    </>
  );
};

export default Photo;
