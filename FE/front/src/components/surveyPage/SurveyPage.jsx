import React from 'react'
import SurveyQuestion from './SurveyQuestion'
import SurveyImages from './SurveyImages'

import styles from './SurveyPage.module.scss'

const SurveyPage = () => {
  return (
    <>
    <div className={styles.survey_page}>
      <SurveyQuestion />
      <SurveyImages />
    </div>
    </>
  )
}

export default SurveyPage