import React from 'react'
import useScrollFadeIn from '../../hooks/useScrollFadeIn'
import init_img_2 from "../../asset/images/init/init2.png"

import styles from "./InitPageThree.module.scss"

const InitPageThree = () => {

  const animationRightItem = useScrollFadeIn('right', 1, 0)

  return (
    <>
      <div className={styles.init_page_three}>
        <div className={styles.text_box} {...animationRightItem}>
          
          <div>
            <div className={styles.init_text}>당신만을 위한</div>
            <span className={`${styles.init_text} ${styles.init_bold_text} `}>향수 추천</span>
          </div>

          <div className={styles.division_line}></div>

          <div>            
            <div className={styles.init_text}>선호하는 향을 알려주시면</div>
            <div className={styles.init_text}>당신이 원하는 취향을</div>
            <div className={styles.init_text}>찾아드릴게요</div>
          </div> 

        </div>

        <img src={init_img_2} alt="init2" className={styles.init_img_2} />

      </div>
    </>
  )
}

export default InitPageThree