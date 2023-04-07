import { useState } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import { Link } from "react-router-dom";
import { userInfo } from "../../store/store";
import { useRecoilValue } from "recoil";

import styles from "./ProfileBox.module.scss";

const ProfileBox = () => {
  const [showProfile, setShowProfile] = useState(false);
  const user = useRecoilValue(userInfo);

  let userName = user.nickname;
  let preference = user.preference;
  // let profileImg = user.imagePath;

  const handleClick = () => {
    setShowProfile(!showProfile);
  };

  const renderingPreference = () => {
    const result = [];
    for (let i = 0; i < preference.length; i++) {
      result.push(<p key={i}>{preference[i]}</p>);
    }
    return result;
  };

  return (
    <>
      <input
        type="checkbox"
        id="check"
        checked={showProfile}
        className={styles.check}
        onChange={handleClick}
      />
      <label htmlFor="check" className={styles.checkbtn}>
        <MenuIcon />
      </label>

      <div className={styles.profile}>
        <div className={styles.profile_img}>
          <img src={user.imagePath} alt="profile_img" />
        </div>
        <div className={styles.user_name}>
          <b>{userName}</b>
        </div>
        <div className={styles.fragrances}>{renderingPreference()}</div>
        <Link to={"/mypage/modify"}>
          <button className={styles.button}>정보수정</button>
        </Link>
        <br />
        <Link to={"/preference"} state={{ state: "modify" }}>
          <button className={styles.button}>향 수정</button>
        </Link>
      </div>
    </>
  );
};

export default ProfileBox;
