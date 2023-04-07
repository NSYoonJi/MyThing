import React, { useState, useRef, useEffect } from "react";
import styles from "./SearchBar.module.scss";
import { getSearchPerfume } from "../../sevices/apis";
import SearchResult from "./SearchResult";
import { Link } from "react-router-dom";

export default function SearchBar({ data }) {
  const [perfumeData, setPerfumeData] = useState([]);
  const [search, setSearch] = useState(""); // 검색어 입력값
  // const [isSubmitted, setIsSubmitted] = useState(false);

  // 검색창 ref 설정
  const searchRef = useRef(null);

  // 검색창 이외의 영역 클릭 시 검색창 닫기
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (searchRef.current && !searchRef.current.contains(event.target)) {
        setSearch("");
        setPerfumeData([]);
        // setIsSubmitted(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [searchRef]);

  // 입력값이 변경될 때마다 search에 검색어 업데이트
  const handleChange = (event) => {
    setSearch(event.target.value);
  };

  // 엔터를 했을 때
  const handleSubmit = (event) => {
    event.preventDefault(); // 리렌더링 막아줌
    fetchSearchData(); // 검색어를 pathvariable로 향수 조회
    // setIsSubmitted(true); // 제출 여부
  };

  // 향수 조회
  const fetchSearchData = async () => {
    const searchData = await getSearchPerfume(search); // search-겁색어
    setPerfumeData(searchData.data);
  };

  return (
    <div ref={searchRef}>
      <form onSubmit={(event) => handleSubmit(event)} className={styles.searchbar_form}>
        <input
          className={styles.searchbar_input}
          type="text"
          placeholder="Search"
          value={search}
          onChange={handleChange}
        />
        {perfumeData.length > 0 && (
          <div className={styles.resultbox}>
            {perfumeData.map((result) => (
              <div key={result.id}>
                <Link to={`/perfume/${result.id}`} style={{ textDecoration: "none" }}>
                  <SearchResult name={result.koName} brand={result.koBrand} />
                </Link>
              </div>
            ))}
          </div>
        )}
        {search !== "" && (
          <button type="button" onClick={() => setSearch("")} className={styles.reset_button}>
            X
          </button>
        )}
      </form>
    </div>
  );
}
