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
    if (isSelected === "") {
      setIsSelected(styles.is_selected);
    } else {
      setIsSelected("");
    }
    console.log(props);
    props.onClick(props);
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
