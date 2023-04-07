import ReactDOM from "react-dom";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";

import styles from "./PhotoModal.module.scss";
import { useEffect, useState } from "react";
import { getReviewPhotoDetail, patchLike } from "../../../../sevices/apis";

const PhotoModal = (props) => {
  const [like, setLike] = useState(false);

  const getLike = async () => {
    const res = await getReviewPhotoDetail(props.data.data.reviewImageId);
    setLike(res.isLike);
  };

  useEffect(() => {
    getLike();
    document.body.style.cssText = `
      position: fixed; 
      top: -${window.scrollY}px;
      overflow-y: scroll;
      width: 100%;`;
    return () => {
      const scrollY = document.body.style.top;
      document.body.style.cssText = "";
      window.scrollTo(0, parseInt(scrollY || "0", 10) * -1);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleLikeChange = () => {
    patchLike({ reviewImageId: props.data.data.reviewImageId, isLike: !like });
    setLike(!like);
  };

  const Backdrop = () => {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
  };

  const ModalOverlay = () => {
    return (
      <div className={styles.box}>
        <img className={styles.image} src={props.data.data.imgURL} alt="reviewimage"></img>;
        {like && <FavoriteIcon className={styles.like} onClick={handleLikeChange} />}
        {!like && <FavoriteBorderIcon className={styles.like} onClick={handleLikeChange} />}
      </div>
    );
  };

  return (
    <>
      {ReactDOM.createPortal(<Backdrop />, document.getElementById("backdrop-root"))}
      {ReactDOM.createPortal(<ModalOverlay />, document.getElementById("overlay-root"))}
    </>
  );
};

export default PhotoModal;
