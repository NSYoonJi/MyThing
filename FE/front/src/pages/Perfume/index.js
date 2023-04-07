import PerfumePage from "../../components/perfumeDetail/Perfume";
import Nav from "../../components/common/Nav";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Perfume = () => {
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
      <PerfumePage />
    </>
  );
};

export default Perfume;
