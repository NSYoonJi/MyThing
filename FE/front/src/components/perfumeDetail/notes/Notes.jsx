import styles from "./Notes.module.scss";
import Triangle from "./triangle/Triangle";

const Notes = (props) => {
  const topNote =
    props.data.length !== 0 ? props.data.topNote.join(", ") : "등록된 정보가 없습니다.";
  const middleNote =
    props.data.length !== 0 ? props.data.middleNote.join(", ") : "등록된 정보가 없습니다.";
  const baseNote =
    props.data.length !== 0 ? props.data.baseNote.join(", ") : "등록된 정보가 없습니다.";
  return (
    <div className={styles.note_box}>
      <h1>Notes</h1>
      <Triangle />
      <div className={styles.top}>
        <p>Top Note</p>
        <div className={styles.line1}></div>
        <div className={styles.notes}>
          <p>{topNote !== [] ? topNote : "등록된 정보가 없습니다."}</p>
        </div>
      </div>
      <div className={styles.middle}>
        <p>Middle Note</p>
        <div className={styles.line2}></div>
        <div className={styles.notes}>
          <p>{middleNote !== "" ? middleNote : "등록된 정보가 없습니다."}</p>
        </div>
      </div>
      <div className={styles.base}>
        <p>Base Note</p>
        <div className={styles.line3}></div>
        <div className={styles.notes}>
          <p>{baseNote !== "" ? baseNote : "등록된 정보가 없습니다."}</p>
        </div>
      </div>
    </div>
  );
};

export default Notes;
