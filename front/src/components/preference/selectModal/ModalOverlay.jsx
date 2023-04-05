import Button from "../../common/button/Button";
import { useRecoilState, useRecoilValue } from "recoil";
import { good, notGood, notes } from "../../../store/store";
import { useEffect, useState } from "react";

import styles from "./ModalOverlay.module.scss";
import NoteItem from "../notes/NoteItem";

const ModalOverlay = (props) => {
  const [goodList, setGoodList] = useRecoilState(good);
  const [notGoodList, setNotGoodList] = useRecoilState(notGood);
  const [selected, setSelected] = useState([]);
  const allNotes = useRecoilValue(notes);

  useEffect(() => {
    if (props.data.data.mode === 0) {
      setSelected(goodList);
    } else {
      setSelected(notGoodList);
    }
  }, [props.data.data.mode, goodList, notGoodList]);

  const handleOnConfirm = () => {
    if (props.data.data.mode === 0) {
      setGoodList([...selected]);
      props.data.onConfirm();
    } else {
      setNotGoodList([...selected]);
      props.data.onConfirm();
    }
  };

  const handleAddGoodList = (data) => {
    console.log(data);
    if (selected.indexOf(data.noteId) === -1) {
      setSelected([...selected, data.noteId]);
    } else {
      let list = [...selected];
      list.splice(list.indexOf(data.noteId), 1);
      setSelected(list);
    }
  };

  const render = () => {
    const result = [];
    for (let i = 0; i < allNotes.length; i++) {
      if (allNotes[i].categoryId === props.data.data.no) {
        let isSelected = false;
        const list = [...selected];
        if (list.indexOf(allNotes[i].noteId) !== -1) {
          isSelected = true;
        }
        result.push(
          <NoteItem
            onClick={handleAddGoodList}
            isSelected={isSelected}
            noteId={allNotes[i].noteId}
            image={allNotes[i].image}
            koName={allNotes[i].koName}
            key={i}
          />
        );
      }
    }
    return result;
  };

  return (
    <div className={styles.modal}>
      <div className={styles.top}>
        <p>{props.data.data.name}</p>
        <div className={styles.note_box}>{render()}</div>
        <div className={styles.button_box}>
          <Button name={"확인"} onClick={handleOnConfirm} />
          <Button name={"취소"} onClick={props.data.onConfirm} />
        </div>
      </div>
    </div>
  );
};

export default ModalOverlay;
