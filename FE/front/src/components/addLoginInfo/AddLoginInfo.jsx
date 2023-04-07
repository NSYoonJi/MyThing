import React, { useState, useEffect } from "react";
import { useRecoilValue } from "recoil";
import { good, notGood } from "../../store/store";
import { postJoinMember, getUserInfo, patchMemberModify } from "../../sevices/apis";
import { useNavigate } from "react-router-dom";
import joinDfault from "../../asset/images/init/joindefault.png"

import styles from "./AddLoginInfo.module.scss";

const AddLoginInfo = ({ mode }) => {
  const navigate = useNavigate();
  const [gender, setGender] = useState("");
  const [nickname, setNickname] = useState("");
  const [nowDate, setNowDate] = useState("");
  const [imgFile, setImgFile] = useState(null);
  const [previewImgFIle, setPreviewImgFIle] = useState(null);
  const [fileName, setfileName] = useState("첨부파일");
  const goodData = useRecoilValue(good);
  const notGoodData = useRecoilValue(notGood);
  const surveyResultPerfume = JSON.parse(localStorage.getItem("surveyResultPerfume"));

  useEffect(() => {
    preview();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [imgFile]);

  useEffect(() => {
    if (!mode) {
      fetchMemberdata();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const fetchMemberdata = async () => {
    const res = await getUserInfo();
    console.log(res.data);
    setNickname(res.data.nickname);
    setNowDate(res.data.birth);
    setGender(res.data.gender === "FEMALE" ? "F" : "M");
    setPreviewImgFIle(res.data.imagePath);
  };

  const preview = () => {
    if (!imgFile) return false;
    const imgReader = new FileReader();

    imgReader.readAsDataURL(imgFile);

    imgReader.onload = () => {
      setPreviewImgFIle(imgReader.result);
    };
  };

  const fetchJoin = async (formData) => {
    try {
      const res = await postJoinMember(formData);
      console.log("로그인성공", res);
    } catch (error) {
      console.log(error);
      alert("회원 가입 실패");
    }
  };

  const fetchModify = async (formData) => {
    try {
      const res = await patchMemberModify(formData);
      console.log("회원 수정 성공", res);
    } catch (error) {
      console.log(error);
      alert("회원 수정 실패");
    }
  };

  const handleGenderChange = (event) => {
    const seletGender = event.target.value;
    if (seletGender === "남자") {
      if (gender === "MALE") {
        setGender("");
        return;
      }
      setGender("MALE");
    } else if (seletGender === "여자") {
      if (gender === "FEMALE") {
        setGender("");
        return;
      }
      setGender("FEMALE");
    }
  };

  const handleNickname = (event) => {
    setNickname(event.target.value);
  };

  const handleDate = (event) => {
    console.log(new Date().toISOString().substr(0, 10))
    setNowDate(event.target.value);
  };

  const handleImage = (event) => {
    if (event.target.files[0]) {
      setImgFile(event.target.files[0]);
      setfileName(event.target.files[0].name);
      console.log(event.target.files[0]);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (mode) {
      handleJoin();
    } else {
      handleModify();
    }
  };

  const handleModify = () => {
    const modifyRequest = {
      nickname: nickname,
      birth: nowDate,
      gender: gender === "F" ? "FEMALE" : "MALE",
    };

    const formData = new FormData();

    formData.append(
      "infoUpdateRequestDto",
      new Blob([JSON.stringify(modifyRequest)], {
        type: "application/json",
      })
    );

    if (imgFile) {
      formData.append("image", imgFile);
    }
    fetchModify(formData);
    navigate("/mypage");
  };

  const handleJoin = () => {
    const joinRequest = {
      nickname: nickname,
      gender: gender,
      date: nowDate,
      prefer_insence: goodData.join(","),
      hate_insence: notGoodData.join(","),
      id: surveyResultPerfume.id,
      name: surveyResultPerfume.name,
      brand: surveyResultPerfume.brand,
      imgURL: surveyResultPerfume.imgURL,
    };

    const formData = new FormData();

    formData.append(
      "signUpRequestDto",
      new Blob([JSON.stringify(joinRequest)], {
        type: "application/json",
      })
    );

    if (imgFile) {
      formData.append("image", imgFile);
    }

    fetchJoin(formData);

    navigate("/mainpage");
  };

  return (
    <>
      <div className={styles.add_info}>
        <form onSubmit={handleSubmit} className={styles.form_box} >
          <div className={styles.img_box}>
            {previewImgFIle === null ?
              (
                <img src={joinDfault} alt="default" className={styles.preview_img} />  
              ) :
              (
                <img src={previewImgFIle} alt="preview" className={styles.preview_img} />
              )
            }
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
          </div>

          <div className={styles.info_box}>
            <h1>{mode ? "회원가입" : "정보수정"}</h1>

            <input
              required
              aria-required="true"
              type="text"
              onChange={handleNickname}
              placeholder="닉네임을 입력해 주세요."
              className={styles.nickname_box}
              value={nickname}
            />
            <span className={styles.warning_message}>*필수 입력 사항입니다.</span>

            <input
              required
              aria-required="true"
              type="date"
              onChange={handleDate}
              className={styles.date_box}
              data-placeholder="생년월일을 선택해주세요."
              max={new Date().toISOString().substr(0, 10)}
            />

            <div className={styles.gender_box}>
              <input
                className={`${styles.gender_button} ${
                  gender === "MALE" ? styles.seleted_gender_box : null
                }`}
                type="button"
                id="male"
                name="gender"
                value="남자"
                onClick={handleGenderChange}
              />
              <input
                className={`${styles.gender_button} ${
                  gender === "FEMALE" ? styles.seleted_gender_box : null
                }`}
                type="button"
                id="female"
                name="gender"
                value="여자"
                onClick={handleGenderChange}
              />
            </div>

            <div className={styles.button_box}>
              <button type="submit" className={styles.submit_button}>
                완료
              </button>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default AddLoginInfo;
