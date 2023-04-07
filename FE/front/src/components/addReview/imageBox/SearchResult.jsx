import React from "react";
import styles from "./SearchResult.module.scss";
import { useSetRecoilState } from "recoil";
import { addReviewPerfume } from "../../../store/store";

export default function SearchResult({ brand, name, data, onClick }) {
  const setPerfumeInfo = useSetRecoilState(addReviewPerfume);

  const handleSelectPerfume = () => {
    setPerfumeInfo({
      koName: data.koName,
      koBrand: data.koBrand,
      image: data.imgURL,
      id: data.id,
    });
    onClick();
  };

  return (
    <div className={styles.search_resultbox} onClick={handleSelectPerfume}>
      <div className={styles.search_result}>
        {"> "}
        {brand} {name}
      </div>
    </div>
  );
}
