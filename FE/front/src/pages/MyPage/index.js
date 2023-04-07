import MyPage from "../../components/myPage/MyPage";
import Nav from "../../components/common/Nav";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const MyPages = () => {
  const navigate = useNavigate();
  useEffect(() => {
    if (sessionStorage.getItem("accessToken") === null) {
      navigate("/");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return (
    <>
      <Nav />
      <MyPage />
    </>
  );
};

export default MyPages;
