import React from "react";
import { Link } from "react-router-dom";
import InitPageButton from "../common/button/InitPageButton";

import styles from "./InitPageOne.module.scss";

const InitPageOne = () => {
  return (
    <>
      <div className={styles.init_page_one}>
        <div className={styles.logo}>취:향</div>
        <div className={styles.info}>당신의 향수를 찾아보아요.</div>
        <div className={styles.button_box}>
          <Link to="/survey/1">
            <InitPageButton text={"취:향 테스트"} />
          </Link>
        </div>
        <div className={styles.login_button}>
          <a
            href={`http://j8b207.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=http://j8b207.p.ssafy.io/oauth/redirect
`}
          >
            로그인
          </a>
        </div>
      </div>
    </>
  );
};

export default InitPageOne;
