import styles from "./Nav.module.scss";
import React, { useEffect, useState } from "react";
import logo from "../../asset/images/logo.png";
import { Link } from "react-router-dom";
import { getUserInfo, getPerfumeInfo } from "../../sevices/apis";

export default function Nav() {
  const [show, setShow] = useState(false);
  const [search, setSearch] = useState("");

  // 검색창
  const onChange = (e) => {
    setSearch(e.target.value);
  };

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

  // 유저 정보 조회
  // const userInfo = getUserInfo().data
  // userInfo.nickname

  // UI
  return (
    <nav className={`${styles.nav} ${show && styles.nav__black}`}>
      <Link to="/">
        <img alt="mything logo" src={logo} className={styles.leftside} />
      </Link>

      <div className={styles.rightside}>
        <input
          className={styles.searchbar}
          type="text"
          placeholder="향수를 입력하시던가."
          value={search}
          onChange={onChange}
        />
        <Link to="/mypage" style={{ textDecoration: "none" }}>
          <p className={styles.link__style}>닉네임 | MYPAGE</p>
        </Link>
      </div>
    </nav>
  );
}
