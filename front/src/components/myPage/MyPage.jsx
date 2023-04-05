import ProfileBox from "./ProfileBox";
import Contents from "./Contents";
import { useEffect } from "react";
import { getUserInfo } from "../../sevices/apis";
import { userInfo } from "../../store/store";
import { useRecoilState } from "recoil";

import styles from "./MyPage.module.scss";

const MyPage = () => {
  const [user, setUser] = useRecoilState(userInfo);

  // 유저 정보 가져오기 로그인 페이지로 옮겨야함
  useEffect(() => {
    const getUser = async () => {
      const res = await getUserInfo();
      setUser(res.data);
    };
    getUser();
  }, [setUser]);

  console.log(user);

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
