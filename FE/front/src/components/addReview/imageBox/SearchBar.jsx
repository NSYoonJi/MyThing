import React, { useState } from "react";
import styles from "./SearchBar.module.scss";
import { getSearchPerfume } from "../../../sevices/apis";
import SearchResult from "./SearchResult";

export default function SearchBar({ data }) {
  const [perfumeData, setPerfumeData] = useState([]);
  const [search, setSearch] = useState(""); // 검색어 입력값

  // 입력값이 변경될 때마다 search에 검색어 업데이트
  const handleChange = (event) => {
    setSearch(event.target.value);
  };

  // 엔터를 했을 때
  const handleSubmit = (event) => {
    event.preventDefault(); // 리렌더링 막아줌
    fetchSearchData(); // 검색어를 pathvariable로 향수 조회
  };

  // 검색창 닫기
  const handleReset = () => {
    setSearch("");
    setPerfumeData([]);
  };

  // 향수 조회
  const fetchSearchData = async () => {
    const searchData = await getSearchPerfume(search); // search-겁색어
    setPerfumeData(searchData.data);
  };

  const render = () => {
    const result = [];
    for (let i = 0; i < perfumeData.length; i++) {
      if (i > 14) break;
      result.push(
        <div key={result.id}>
          <SearchResult
            name={perfumeData[i].koName}
            brand={perfumeData[i].koBrand}
            data={perfumeData[i]}
            onClick={handleReset}
          />
        </div>
      );
    }
    return result;
  };

  return (
    <div>
      <form onSubmit={(event) => handleSubmit(event)} className={styles.searchbar_form}>
        <input
          className={styles.searchbar_input}
          type="text"
          placeholder="Search"
          value={search}
          onChange={handleChange}
        />
        <div className={styles.resultbox}>{render()}</div>
        {search !== "" && (
          <button type="button" onClick={handleReset} className={styles.reset_button}>
            X
          </button>
        )}
      </form>
    </div>
  );
}
