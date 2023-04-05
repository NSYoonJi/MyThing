import styles from "./ImageBox.module.scss";
import perfume_img from "../../../asset/images/perfume_img.png";

const ImageBox = () => {
  return (
    <>
      <div className={styles.perfume_img}>
        <div className={styles.box}>
          <div className={styles.search_bar}>Search Bar</div>
          <div className={styles.image_box}>
            <img src={perfume_img} alt="perfume" />
          </div>
          <div className={styles.name_box}>
            <p>eau perfume tamdao</p>
            <p>
              <b>CHANEL</b>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default ImageBox;
