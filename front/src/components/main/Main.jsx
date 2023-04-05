// import { Link } from "react-router-dom";
import BannerFirst from "./BannerFirst";
import BannerSecond from "./BannerSecond";
import Row from "./Row";

const Main = () => {
  // 로컬스토리지 확인
  const perfumeId = localStorage.getItem('selectedId');
  
  return (
    <>
      <div>
        <BannerFirst />
        <Row
          title="지금 인기 있는"
          id="TN"
          indexNo={0}
        />
        <BannerSecond />
        {perfumeId != null ? (
          <Row
            title="당신이 좋아할 만한"
            id="CR"
            indexNo={2}
          />
        ) : (
          null
        )}
        <Row
          title="당신과 비슷한"
          id="AG"
          indexNo={1}
        />
      </div>
    </>
  );
};

export default Main;
