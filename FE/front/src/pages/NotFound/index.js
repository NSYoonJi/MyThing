import { useNavigate } from "react-router-dom";
import notFoundImg from "../../asset/images/not_found.png";
import Button from "../../components/common/button/Button";

const NotFoundPage = () => {
  const navigate = useNavigate();

  const handleRedirectMain = () => {
    navigate("/");
  };

  return (
    <>
      <div
        style={{ width: "100%", height: "100vh", backgroundColor: "#F5EBE0", overflow: "hidden" }}
      >
        <img
          src={notFoundImg}
          alt="notfound"
          style={{ width: "100%", height: "100%", objectFit: "cover" }}
        ></img>
        <div
          style={{
            zIndex: "100",
            position: "fixed",
            top: "70%",
            left: "46%",
          }}
        >
          <Button name={"돌아가기"} onClick={handleRedirectMain} />
        </div>
      </div>
    </>
  );
};

export default NotFoundPage;
