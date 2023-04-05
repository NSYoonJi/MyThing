import React from 'react'
import styles from "./Perfume.module.scss"

export default function Perfume({ imgURL, name, brand }) {

// UI
return (
    <div className={styles.items}>
        <img
            src={imgURL}
            className={styles.perfume__img}
            alt="향수 이미지"
        />
        <div className={styles.perfume__brand}>{ brand }</div>
        <div className={styles.perfume__name}>{ name }</div>
</div>
)
}
