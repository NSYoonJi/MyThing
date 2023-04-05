import React, { useState, useEffect } from "react";
import { useRecoilValue } from "recoil";
import { good, notGood, surveyResult } from "../../store/store";
import { postJoinMember } from "../../sevices/apis";

import styles from "./AddLoginInfo.module.scss";

const AddLoginInfo = () => {
  const [gender, setGender] = useState("");
  const [nickname, setNickname] = useState("");
  const [date, setDate] = useState("");
  const [imgFile, setImgFile] = useState(null);
  const [previewImgFIle, setPreviewImgFIle] = useState(null);
  const goodData = useRecoilValue(good);
  const notGoodData = useRecoilValue(notGood);
  const surveyResultData = useRecoilValue(surveyResult);

  useEffect(() => {
    preview();
  }, [imgFile])

  const preview = () => {
    if (!imgFile) return false;
    const imgReader = new FileReader();

    imgReader.readAsDataURL(imgFile);

    imgReader.onload = () => {
      setPreviewImgFIle(imgReader.result)
    }
  }

  const fetchJoin = async (formData) => {
    const res = await postJoinMember(formData);
    console.log(res);
  }

  const handleGenderChange = (event) => {
    setGender(event.target.value === "male" ? "M" : "F");
  };

  const handleNickname = (event) => {
    setNickname(event.target.value);
  };

  const handleDate = (event) => {
    setDate(event.target.value);
  };

  const handleImage = (event) => {
    setImgFile(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const joinRequest = {
      nickname: nickname,
      gender: gender,
      date: date,
      prefer_insence: goodData.join(","),
      hate_insence: notGoodData.join(","),
      id: surveyResultData.id,
      name : surveyResultData.name,
      brand : surveyResultData.brand,
      imgURL : surveyResultData.imgURL
    }

    const formData = new FormData();

    formData.append("tempRequest", JSON.stringify({ ...joinRequest }), {
      type: "application/json",
    });

    formData.append("img", imgFile);

    fetchJoin(formData);
  }

  return (
    <>
      <div className={styles.add_info}>
        <form onSubmit={handleSubmit} className={styles.form_box}>

          <div className={styles.img_box}>
            {previewImgFIle && <img src={previewImgFIle} alt="preview" className={styles.preview_img} />}
            <input type="file" accept="image/*" onChange={handleImage}/>
          </div>

          <div className={styles.info_box}>
            <input type="text" onChange={handleNickname} placeholder="닉네임을 입력해 주세요." />
            <input type="date" onChange={handleDate} placeholder="연령대를 선택해주세요." />

            <div>
              <label htmlFor="male">
                <input
                  type="button"
                  id="male"
                  name="gender"
                  value="남자"
                  onClick={handleGenderChange}
                />
              </label>
              <label htmlFor="female">
                <input
                  type="button"
                  id="female"
                  name="gender"
                  value="여자"
                  onClick={handleGenderChange}
                />
              </label>
            </div>
            <button type="submit">완료</button>
          </div>
        </form>
      </div>
    </>
  );
};

export default AddLoginInfo;
