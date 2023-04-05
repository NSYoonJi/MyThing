import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useLocation } from "react-router-dom";
import { useRecoilState } from "recoil";
import { good, notGood, notes } from "../../store/store";
import Button from "../common/button/Button";
import SelectModal from "./selectModal/SelectModal";
import NoteClass from "./notes/NoteClass";
import SelectedNote from "./SelectedNote";
import { getNoteList } from "../../sevices/apis";

import styles from "./Preference.module.scss";

const Preference = () => {
  const navigate = useNavigate();
  const [select, setSelect] = useState(null);
  const [mode, setMode] = useState(0); // 0 == 선호향 1 == 불호향

  const classes = [
    { no: 1, name: "CITRUS" },
    { no: 2, name: "FRUITS, VEGETABLES AND NUTS" },
    { no: 3, name: "FLOWERS" },
    { no: 4, name: "WHITE FLOWERS" },
    { no: 5, name: "GREENS, HERBS AND FOUGERES" },
    { no: 6, name: "SPICES" },
    { no: 7, name: "SWEETS AND GOURMAND SMELLS" },
    { no: 8, name: "WOODS AND MOSSES" },
    { no: 9, name: "RESINS AND BALSAMS" },
    { no: 10, name: "MUSK, AMBER AND ANIMAL" },
    { no: 12, name: "NATURAL AND SYNTHETIC, POPULAR AND WEIRD" },
  ];

  const location = useLocation();

  let state = "";
  if (location.state !== null) {
    state = location.state.state;
  }

  const [allNotes, setAllNotes] = useRecoilState(notes);

  useEffect(() => {
    const fetchNoteLists = async () => {
      const result = await Promise.all(classes.map((name) => getNoteList(name.name)));
      const mergedResult = result.flatMap((data) => data.data);
      setAllNotes(mergedResult);
    };
    fetchNoteLists();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (allNotes !== null) {
  }

  useEffect(() => {
    if (state === "modify") {
      // 수정페이지면 기존 향 가져와야함
      console.log("load data");
    }
  }, [state]);

  const [selectedItemGood, setSelectedItemGood] = useRecoilState(good);
  const [selectedItemNotGood, setSelectedItemNotGood] = useRecoilState(notGood);

  const ment = mode === 0 ? "좋아하는 향을 선택해주세요" : "싫어하는 향을 선택해주세요";
  const btnName = mode === 0 ? "다음" : "완료";

  const handleOnSelect = (data) => {
    setSelect({ ...data, mode: mode });
  };

  const handleOnConfirm = () => {
    setSelect(null);
  };

  const deleteNote = (no) => {
    if (mode === 0) {
      let list = [...selectedItemGood];
      list.splice(list.indexOf(no), 1);
      setSelectedItemGood(list);
    } else {
      let list = [...selectedItemNotGood];
      list.splice(list.indexOf(no), 1);
      setSelectedItemNotGood(list);
    }
  };

  const handleChangeMode = () => {
    if (mode === 0) {
      setMode(1);
    } else {
      // 저장요청
      // 데이터 형식은 "ㅁ,ㅁ,ㅁ,ㅁ,ㅁ" 컴마로 구분한 string
      // 다음 페이지로
      if (state === "modify") {
        navigate("/mypage");
      } else {
        navigate("/");
      }
    }
  };

  const renderNoteClass = () => {
    const result = [];
    for (let i = 0; i < classes.length; i++) {
      if (mode === 0) {
        let isSelected = false;
        for (let j = 0; j < selectedItemGood.length; j++) {
          const data = allNotes.find((item) => item.noteId === selectedItemGood[j]);
          if (data !== undefined && data.categoryId === classes[i].no) {
            isSelected = true;
          }
        }

        result.push(
          <NoteClass
            onClick={handleOnSelect}
            selected={selectedItemGood}
            name={classes[i].name}
            no={classes[i].no}
            isSelected={isSelected}
            key={i}
          />
        );
      } else {
        let isSelected = false;
        for (let j = 0; j < selectedItemNotGood.length; j++) {
          const data = allNotes.find((item) => item.noteId === selectedItemNotGood[j]);
          if (data !== undefined && data.categoryId === classes[i].no) {
            isSelected = true;
          }
        }
        result.push(
          <NoteClass
            onClick={handleOnSelect}
            selected={selectedItemNotGood}
            name={classes[i].name}
            no={classes[i].no}
            isSelected={isSelected}
            key={i}
          />
        );
      }
    }
    return result;
  };

  const renderSelectedNotes = () => {
    const result = [];
    if (mode === 0) {
      for (let i = 0; i < selectedItemGood.length; i++) {
        const data = allNotes.find((item) => item.noteId === selectedItemGood[i]);
        if (data !== undefined) {
          result.push(
            <SelectedNote key={i} no={data.noteId} name={data.koName} onClick={deleteNote} />
          );
        }
      }
    } else {
      for (let i = 0; i < selectedItemNotGood.length; i++) {
        const data = allNotes.find((item) => item.noteId === selectedItemNotGood[i]);
        if (data !== undefined) {
          result.push(
            <SelectedNote key={i} no={data.noteId} name={data.koName} onClick={deleteNote} />
          );
        }
      }
    }
    return result;
  };

  return (
    <>
      {select && <SelectModal data={select} onConfirm={handleOnConfirm} />}
      <div className={styles.top}>
        <p className={styles.p}>{ment}</p>
        <div className={styles.selected_box}>{renderSelectedNotes()}</div>
        <div className={styles.preference_class}>{renderNoteClass()}</div>
        <div className={styles.button_box}>
          <Button name={btnName} onClick={handleChangeMode} />
        </div>
      </div>
    </>
  );
};

export default Preference;
