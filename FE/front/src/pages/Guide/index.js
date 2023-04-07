import { useEffect } from "react";
import Nav from "../../components/common/Nav";
import Guide from "../../components/guide/Guide";
import { useNavigate } from "react-router-dom";

const GuidePage = () => {
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
      <Guide />
    </>
  );
};

export default GuidePage;
