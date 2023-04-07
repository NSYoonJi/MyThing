import React from 'react'
import styles from "./SearchResult.module.scss";

export default function SearchResult({brand, name}) {

    return (
        <div className={styles.search_resultbox}>
            <div className={styles.search_result}>
                {"> "}{brand}{" "}{name}
            </div>
        </div>
    )
}
