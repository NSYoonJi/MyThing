import React from "react";

import styles from "./InitPageButton.module.scss";

const InitpageButton = ({ text }) => {
  return (
    <>
      <button className={styles.start_button}>{text}</button>
    </>
  );
};

export default InitpageButton;
