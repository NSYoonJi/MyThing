import React, { useEffect } from 'react'
import useScrollFadeIn from '../../hooks/useScrollFadeIn'
import InitpageButton from '../common/button/InitPageButton'
import init_img3 from "../../asset/images/init/init3.png"

import styles from "./InitPageFour.module.scss"

const InitPageFour = () => {

  const animationleftItem = useScrollFadeIn('left', 1, 0)

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <>
      <div className={styles.init_page_four}>
        
        <img src={init_img3} alt="init3" className={styles.init_img1}/>
        
        <div className={styles.text_box1} {...animationleftItem} >
          
          <div>
            <div className={styles.init_text}>당신과 비슷한</div>
            <div className={styles.init_text}>사람</div>
          </div>

          <div className={styles.division_line}></div>

          <div>
            <div>
              <span className={styles.init_text}>당신과 </span>
              <span className={`${styles.init_text} ${styles.init_bold_text} `}>비슷한 취향</span>
              <span className={styles.init_text}>을 가진</span>
            </div>
            <div className={styles.init_text}>사람들이 좋아하는</div>
            <div className={styles.init_text}>향수 추천</div>
          </div>

        </div>
        
      </div>

      <div className={styles.init_page_end}>
        <div onClick={() => window.scrollTo({top:0, left:0, behavior:'smooth'})}>
          <InitpageButton text={"시작해보기"}/>
        </div>
        
      </div>
    </>
  )
}

export default InitPageFour