import Perfume from "./row/Perfume";
import React, { useEffect, useState } from "react";
import { getPopularPerfume, getAgeGenderPerfume, getClickPerfume } from "../../sevices/apis";
import styles from "./Row.module.scss";
import { Link } from "react-router-dom";

import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';


export default function Row({ title, id, indexNo }) {
  const [populars, setPopulars] = useState([]);
  const [ages, setAges] = useState([]);
  const [clicks, setClicks] = useState([]);

  // 향수 정보 가져오기
  useEffect(() => {
    fetchPerfumeData();
  }, []);

  const fetchPerfumeData = async () => {
    // 인기 차트
    const popularRes = await getPopularPerfume();
    setPopulars(popularRes.data);

    // 연령
    const ageRes = await getAgeGenderPerfume();
    setAges(ageRes.data);

    // 클릭
    // 클릭한 향수의 id
    const perfumeId = localStorage.getItem("selectedId");

    if (perfumeId !== null) {
      const clickRes = await getClickPerfume(perfumeId);
      setClicks(clickRes.data);
    }
  };

  // 클릭 기반 추천
  const handleClick = (id) => {
    // 로컬 스토리지에 데이터 저장
    localStorage.setItem("selectedId", id);
  };

  /////////////////////////////////////////////////

  // 현재 페이지 번호를 상태로 관리
  const [currentPage, setCurrentPage] = useState(1);
  const minPage = 1;
  const maxPage = 3;

  const newArray = [populars, ages, clicks];

  // 보여줄 향수 필터링
  const filterPerfumes = (array) => {
    const startIndex = (currentPage - 1) * 4;
    const endIndex = startIndex + 4;
    return array.slice(startIndex, endIndex);
  };

  // 이전 페이지로 이동
  const gotoPrevPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, minPage));
  };

  // 다음 페이지로 이동
  const gotoNextPage = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, maxPage));
  };

  /////////////////////////////////////////////////

  // UI
  return (
    <section className={styles.row}>
      <h1>{title}</h1>

      <div className={styles.row__perfumes}>
        <div className={styles.row__perfume} onClick={gotoPrevPage}>
            <ArrowBackIosIcon  fontSize="48px" style={{ color: '#8F8F8F' }} /> 
        </div>
        {filterPerfumes(newArray[indexNo]).map((perfume) => (
          <Link to={`/perfume/${perfume.id}`} style={{ textDecoration: "none" }} key={ perfume.id }>
            <span
              className={styles.click__style}
              onClick={() => handleClick(perfume.id)}
            >
              <Perfume
                imgURL={perfume.imgURL}
                name={perfume.name}
                brand={perfume.brand}
              />
            </span>
          </Link>
        ))}

        <div className={styles.row__perfume} onClick={gotoNextPage}>
            <ArrowForwardIosIcon  fontSize="48px" style={{ color: '#8F8F8F' }} /> 
        </div>
      </div>
    </section>
  );
}
