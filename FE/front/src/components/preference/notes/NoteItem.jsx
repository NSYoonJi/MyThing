import { useEffect, useState } from "react";
import styles from "./NoteItem.module.scss";
import checked from "../../../asset/images/checked.png";

const NoteItem = (props) => {
  const [isSelected, setIsSelected] = useState("");

  useEffect(() => {
    if (props.isSelected === true) {
      setIsSelected(styles.is_selected);
    }
  }, [props.isSelected]);

  const no = props.noteId;

  const handleAddGoodList = () => {
    if (props.count < 5) {
      if (isSelected === "") {
        setIsSelected(styles.is_selected);
      } else {
        setIsSelected("");
      }
      props.onClick(props);
    } else {
      alert("최대 5개까지만 선택 가능합니다.");
    }
  };

  return (
    <div key={no} className={`${styles.note_item}`}>
      <div className={styles.image_box}>
        <img className={`${styles.checked} ${isSelected}`} src={checked} alt="checked" />
        <img
          className={styles.image}
          src={props.image}
          alt={no}
          draggable="false"
          onClick={handleAddGoodList}
        />
      </div>

      <p>{props.koName}</p>
    </div>
  );
};

export default NoteItem;
