import BannerFirst from "./BannerFirst";
import BannerSecond from "./BannerSecond";
import Row from "./Row";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect } from "react";

const Main = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    saveToken();
    window.scrollTo({ top: 0, behavior: "smooth" });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const saveToken = () => {
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get("code");

    if (token !== null) {
      sessionStorage.setItem("accessToken", token);
      navigate("/mainpage");
    }
  };

  // 로컬스토리지 확인
  const perfumeId = localStorage.getItem("selectedId");

  return (
    <>
      <div>
        <BannerFirst />
        <Row title="지금 인기 있는" id="TN" indexNo={0} />
        <BannerSecond />
        {perfumeId != null ? <Row title="당신이 좋아할 만한" id="CR" indexNo={2} /> : null}
        <Row title="당신과 비슷한" id="AG" indexNo={1} />
      </div>
    </>
  );
};

export default Main;
