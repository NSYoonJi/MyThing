import { useEffect } from "react";
import AddReivew from "../../components/addReview/AddReview";
import { useNavigate } from "react-router-dom";

const AddReview = () => {
  const navigate = useNavigate();
  useEffect(() => {
    if (sessionStorage.getItem("accessToken") === null) {
      navigate("/");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return (
    <>
      <AddReivew />
    </>
  );
};

export default AddReview;
