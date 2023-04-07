import Main from "../../components/main/Main";
import Nav from "../../components/common/Nav";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
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
      <Main />
    </>
  );
};

export default MainPage;
