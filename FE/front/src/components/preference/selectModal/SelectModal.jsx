import ReactDOM from "react-dom";
import ModalOverlay from "./ModalOverlay";

import styles from "./SelectModal.module.scss";

const SelectModal = (props) => {
  const Backdrop = (props) => {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
  };

  return (
    <>
      {ReactDOM.createPortal(<Backdrop />, document.getElementById("backdrop-root"))}
      {ReactDOM.createPortal(
        <ModalOverlay data={props} />,
        document.getElementById("overlay-root")
      )}
    </>
  );
};

export default SelectModal;
