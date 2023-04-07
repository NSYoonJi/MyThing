import ProfileBox from "./ProfileBox";
import Contents from "./Contents";
import { useEffect } from "react";
import { getUserInfo } from "../../sevices/apis";
import { userInfo } from "../../store/store";
import { useSetRecoilState } from "recoil";

import styles from "./MyPage.module.scss";

const MyPage = () => {
  const setUser = useSetRecoilState(userInfo);

  useEffect(() => {
    const getUser = async () => {
      const res = await getUserInfo();
      setUser(res.data);
    };
    getUser();
  }, [setUser]);

  return (
    <>
      <div className={styles.my_page}>
        <ProfileBox />
        <Contents />
      </div>
    </>
  );
};

export default MyPage;
