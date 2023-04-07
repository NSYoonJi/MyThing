import React, { useEffect } from 'react'
import AddLoginInfo from "../../components/addLoginInfo/AddLoginInfo"
import { useNavigate } from "react-router-dom";


const ProfileModifyPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    if (sessionStorage.getItem("accessToken") === null) {
      navigate("/");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return (
    <>
      <AddLoginInfo mode={false} />
    </>
  )
}

export default ProfileModifyPage