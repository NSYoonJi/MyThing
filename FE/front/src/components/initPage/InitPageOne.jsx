import React from "react";
import { Link } from "react-router-dom";
import InitPageButton from "../common/button/InitPageButton";
import logo_200 from "../../asset/images/logo/logo_200.png";
import ArrowDownwardRoundedIcon from "@mui/icons-material/ArrowDownwardRounded";

import styles from "./InitPageOne.module.scss";
import { mode } from "../../constants";

const InitPageOne = () => {
  return (
    <>
      <div className={styles.init_page_one}>
        <div className={styles.top_style}>
          <div className={styles.logo}>
            <img src={logo_200} alt="로고"/>
          </div>
          <div className={styles.info}>당신의 향수를 찾아보아요.</div>
          <div className={styles.button_box}>
            <Link to="/survey/1">
              <InitPageButton text={"취:향 테스트"} />
            </Link>
          </div>
          <div className={styles.login_button}>
            {mode === "local" && (
              <a
                className={styles.login_text}
                href={`http://j8b207.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=http://localhost:3000/oauth/redirect`}
              >
                로그인
              </a>
            )}
            {mode === "server" && (
              <a
                className={styles.login_text}
                href={`http://j8b207.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=http://j8b207.p.ssafy.io/oauth/redirect`}
              >
                로그인
              </a>
            )}
          </div>
        </div>
        <div className={styles.bottom_style}>
          <div className={styles.arrow}>
            <ArrowDownwardRoundedIcon style={{ fontSize: "70px", color: "#8F8F8F" }} />
          </div>
          <div className={styles.text_style}>Scroll</div>
        </div>
      </div>
    </>
  );
};

export default InitPageOne;
