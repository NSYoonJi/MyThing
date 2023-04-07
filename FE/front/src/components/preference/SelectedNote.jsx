import CloseIcon from "@mui/icons-material/Close";

import styles from "./SelectedNote.module.scss";

const SelectedNote = (props) => {
  const handleDelete = () => {
    props.onClick(props.no);
  };
  return (
    <div className={styles.div}>
      <p>{props.name}</p> <CloseIcon className={styles.icon} onClick={handleDelete} />
    </div>
  );
};

export default SelectedNote;
