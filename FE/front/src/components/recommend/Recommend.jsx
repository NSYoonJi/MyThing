import PerfumeItem from "./perfumeItem/PerfumeItem";
import pinset from "../../asset/images/pinset.png";
import { useEffect, useState } from "react";
import Loading from "../common/Loading";
import { getRecommendPerfume } from "../../sevices/apis";

import styles from "./Recommend.module.scss";

const Recommend = () => {
  const [color, setColor] = useState("#fffbea");
  const [name, setName] = useState("취향 기반 추천");
  const [perumeList, setPerfumeList] = useState([]);
  const [loading, setLoading] = useState(null); // 로딩

  useEffect(() => {
    setLoading(true); // 스피너
  }, [name]);

  const getPerfume = async () => {
    setPerfumeList(await getRecommendPerfume("survey"));
    setLoading(false);
  };

  useEffect(() => {
    getPerfume();
  }, []);

  const handleChange = async (data, no) => {
    switch (no) {
      case 1:
        setName("취향 기반 추천");
        setPerfumeList(await getRecommendPerfume("survey"));
        setLoading(false);
        break;
      case 2:
        setName("반대되는 취향 추천");
        setPerfumeList(await getRecommendPerfume("survey-reverse"));
        setLoading(false);
        break;
      case 3:
        setName("선호향 기반 추천");
        setPerfumeList(await getRecommendPerfume("prefer"));
        setLoading(false);
        break;
      case 4:
        setName("리뷰 기반 추천");
        setPerfumeList(await getRecommendPerfume("recommend-review"));
        setLoading(false);
        break;

      default:
        setName("");
        break;
    }
    setColor(data);
  };

  const renderingPinset = () => {
    const result = [];
    for (let i = 0; i < perumeList.length; i++) {
      if (i > 4) break;
      result.push(<img src={pinset} alt="pinset" key={i} />);
    }

    return result;
  };

  const renderingPreference = () => {
    const result = [];
    if (perumeList.length === 1) {
      return <h1>리뷰를 등록하고 추천을 받아보세요</h1>;
    }
    for (let i = 0; i < perumeList.length; i++) {
      if (i > 4) break;
      result.push(<PerfumeItem data={perumeList[i]} color={color} key={i} />);
    }

    return result;
  };

  return (
    <>
      {loading && <Loading />}
      <div className={styles.top}>
        <div className={styles.bg}>
          <h1>{name}</h1>{" "}
        </div>
        <div className={styles.item_pinset}>{renderingPinset()}</div>
        <div className={styles.item}>{renderingPreference()}</div>
      </div>
      <div className={styles.container}>
        <div
          className={`${styles.slice} ${styles.slice1}`}
          onClick={() => {
            handleChange("#fffbea", 1);
          }}
        ></div>
        <div
          className={`${styles.slice} ${styles.slice2}`}
          onClick={() => {
            handleChange("#f5ebe0", 2);
          }}
        ></div>
        <div
          className={`${styles.slice} ${styles.slice3}`}
          onClick={() => {
            handleChange("#f0dbdb", 3);
          }}
        ></div>
        <div
          className={`${styles.slice} ${styles.slice4}`}
          onClick={() => {
            handleChange("#ecbdbd", 4);
          }}
        ></div>
      </div>
    </>
  );
};

export default Recommend;
