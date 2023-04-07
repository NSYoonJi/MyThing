import { useEffect, useState } from "react";
import default_image from "../../../asset/images/review/default_image.png";

import styles from "./ImageBox.module.scss";
import SearchBar from "./SearchBar";
import { useRecoilState, useRecoilValue } from "recoil";
import { addReviewImage, addReviewPerfume } from "../../../store/store";

const ImageBox = () => {
  const [imgFile, setImgFile] = useRecoilState(addReviewImage);
  const [defaultImg, setDefaultImg] = useState(default_image);
  const [previewImgFIle, setPreviewImgFIle] = useState(null);
  const [fileName, setfileName] = useState("첨부파일");
  const perfumeInfo = useRecoilValue(addReviewPerfume);

  useEffect(() => {
    addImage();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [imgFile]);

  useEffect(() => {
    setPreviewImgFIle(null);
    setImgFile(null);
    setfileName("첨부파일");
    console.log(123);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [perfumeInfo]);

  useEffect(() => {
    setPreviewImgFIle(null);
    setImgFile(null);
    setfileName("첨부파일");
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const addImage = () => {
    if (!imgFile) return false;
    console.log(456);
    const imgReader = new FileReader();

    imgReader.readAsDataURL(imgFile);
    setDefaultImg(null);
    imgReader.onload = () => {
      setPreviewImgFIle(imgReader.result);
    };
  };

  const handleImage = (event) => {
    if (event.target.files[0]) {
      setfileName(event.target.files[0].name);
      setImgFile(event.target.files[0]);
    }
  };

  return (
    <>
      <div className={styles.perfume_img}>
        <div className={styles.box}>
          <div className={styles.search_bar}>
            <SearchBar />
          </div>
          <div className={styles.image_box}>
            {defaultImg && !perfumeInfo && (
              <img src={defaultImg} alt="preview" className={styles.preview_img} />
            )}
            {perfumeInfo && !previewImgFIle && (
              <img src={perfumeInfo.image} alt="preview" className={styles.perfume_img} />
            )}
            {previewImgFIle && (
              <img src={previewImgFIle} alt="preview" className={styles.preview_img} />
            )}

            {perfumeInfo && (
              <div className={styles.file_select_box}>
                <div className={styles.upload_file_name}>{fileName}</div>
                <label htmlFor="file" className={styles.label}>
                  파일 찾기
                </label>
                <input
                  type="file"
                  id="file"
                  accept="image/*"
                  onChange={handleImage}
                  className={styles.file_input}
                />
              </div>
            )}
          </div>
          <div className={styles.name_box}>
            <p>{perfumeInfo && perfumeInfo.koName}</p>
            <p>
              <b>{perfumeInfo && perfumeInfo.koBrand}</b>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default ImageBox;
