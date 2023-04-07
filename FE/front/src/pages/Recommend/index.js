import { useEffect } from "react";
import Nav from "../../components/common/Nav";
import Recommend from "../../components/recommend/Recommend";
import { useNavigate } from "react-router-dom";

const RecommendPage = () => {
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
      <Recommend />
    </>
  );
};

export default RecommendPage;
