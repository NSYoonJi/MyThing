import SearchBar from "./SearchBar";
import styles from "./Nav.module.scss";
import React, { useEffect, useState } from "react";
import logo from "../../asset/images/logo.png";
import { Link } from "react-router-dom";
import { getUserInfo } from "../../sevices/apis";

export default function Nav() {
  const [show, setShow] = useState(false);
  const [nickname, setNickname] = useState("");

  // 유저 정보 가져오기
  const token = sessionStorage.getItem("accessToken");

  const fetchUserData = async () => {
    const res = await getUserInfo();
    setNickname(res.data.nickname);
  };

  // 토큰 존재 유무 확인
  if (token) {
    // 토큰있으면 요청보내면 알아서 백에서 일치하는 유저의 정보를 넘겨줌
    fetchUserData();
  } else {
    // 없으면 로그인 페이지
    // window.location.href = "http://j8b207.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=http://j8b207.p.ssafy.io/oauth/redirect";
  }

  // scroll
  useEffect(() => {
    window.addEventListener("scroll", () => {
      if (window.scrollY > 50) {
        setShow(true); // nav__black 이용
      } else {
        setShow(false); // nav만 이용
      }
    });

    return () => {
      window.removeEventListener("scroll", () => {});
    };
  }, []);

  // UI
  return (
    <nav className={`${styles.nav} ${show && styles.nav__black}`}>
      <Link to="/mainpage">
        <img alt="mything logo" src={logo} className={styles.leftside} />
      </Link>
      <div className={styles.rightside}>
        <SearchBar />
        <Link to="/mypage" style={{ textDecoration: "none" }}>
          <p className={styles.link__style}>{nickname} | MYPAGE</p>
        </Link>
      </div>
    </nav>
  );
}
