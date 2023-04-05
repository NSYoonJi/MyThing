import { useParams } from "react-router";
import PerfumeBox from "./perfume/PerfumeBox";
import PhotoBox from "./photo/PhotoBox";
import Notes from "./notes/Notes";
import Desc from "./desc/Desc";
import Reviews from "./review/Reviews";

import styles from "./Perfume.module.scss";
import { useEffect, useRef, useState } from "react";
import { getPerfumeInfo } from "../../sevices/apis";

const Perfume = () => {
  const { no } = useParams();
  const [perfumeInfo, setPerfumeInfo] = useState([]);
  const topRef = useRef(null);

  // no로 향수 로딩
  useEffect(() => {
    const getPerfume = async () => {
      setPerfumeInfo(await getPerfumeInfo(no));
    };
    getPerfume();
  }, [no]);

  useEffect(() => {
    topRef.current.scrollTop = 0;
  }, []);

  return (
    <div className={styles.top} ref={topRef}>
      <PerfumeBox data={perfumeInfo} />
      <PhotoBox data={perfumeInfo} />
      <Notes data={perfumeInfo} />
      <Desc data={perfumeInfo} />
      <Reviews data={perfumeInfo} />
    </div>
  );
};

export default Perfume;
