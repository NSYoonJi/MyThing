import React from 'react'
import { ScaleLoader } from "react-spinners";
import styles from "./Loading.module.scss"

export default function Loading() {

return (
    <div className={ styles.contentWrap }>
        <div className={ styles.loading }>
        <ScaleLoader
            color="#ECBDBD"
            cssOverride={{}}
            height={81}
            loading
            margin={5}
            radius={19}
            speedMultiplier={1}
            width={14}
        />
        </div>
    </div>
)
}
