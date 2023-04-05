import { useEffect, useState } from "react";
import { getUserServey } from "../../../sevices/apis";
import PerfumeItem from "./PerfumeItem";

import styles from "./Servey.module.scss";

const Servey = () => {
  const [survey, setServey] = useState([]);

  useEffect(() => {
    const getServey = async () => {
      const res = await getUserServey();
      setServey(res.data);
    };
    getServey();
  }, [setServey]);

  const render = () => {
    const result = [];
    for (let i = 0; i < survey.length; i++) {
      result.push(<PerfumeItem data={survey[i]} key={i} />);
    }
    return result;
  };

  return (
    <>
      <p>다시보고 싶을까봐, 따로 챙겨놨어요. 멘트 구리답니다</p>
      <div className={styles.perfumes}>{render()}</div>
    </>
  );
};

export default Servey;
