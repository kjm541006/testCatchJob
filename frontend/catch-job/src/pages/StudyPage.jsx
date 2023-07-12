import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import styles from "../assets/css/Study.module.css";
import axios from "axios";

const StudyPage = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get("https://jsonplaceholder.typicode.com/posts/99").then((res) => setData(res.data));
  }, []);

  // const dummyTags = [
  //   { location: "온라인" },
  //   { name: "프로그래밍" },
  //   { name: "3개월" },
  //   { name: "온라인" },
  //   { name: "온라인" },
  //   { name: "온라인" },
  //   { name: "온라인" },
  //   { name: "온라인" },
  // ];

  return (
    <div className={styles.studyPage}>
      <div className={styles.studySort}>
        <FontAwesomeIcon icon={faCheck} className={styles.checkIcon} />
        <span className={`${styles.topRated} ${styles.btn}`}>인기순</span>
        <FontAwesomeIcon icon={faCheck} className={`${styles.checkIcon} ${styles.invisible}`} />
        <span className={styles.new}>최신순</span>
      </div>
      <div className={styles.studyGridView}>
        {/* key db id로 변경해야함 */}
        {data.map((v, i) => {
          return (
            <div key={i} className={styles.studyGridElement}>
              <div className={styles.type}>
                {true && (
                  <>
                    <div className={styles.typeWord}>S</div>
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
                      <rect x="2.01416" width="25.0439" height="18" fill="#F9DBA1" />
                      <path d="M14.5361 30L1.95669 18H27.1155L14.5361 30Z" fill="#F9DBA1" />
                    </svg>
                  </>
                )}
                {v.type === "project" && (
                  <>
                    <div className={styles.typeWord}>P</div>
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
                      <rect x="2.01416" width="25.0439" height="18" fill="#D3B4F2" />
                      <path d="M14.5361 30L1.95669 18H27.1155L14.5361 30Z" fill="#D3B4F2" />
                    </svg>
                  </>
                )}
              </div>
              {true && (
                <div className={styles.heart}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" fill="none">
                    <path
                      d="M11.4081 1.47208C11.9976 1.16786 12.6398 1.00685 13.2911 1.00003C13.8378 0.997993 14.3792 1.1156 14.8839 1.34599C15.3891 1.57661 15.8474 1.91559 16.2318 2.34308C16.6163 2.77057 16.9191 3.27798 17.1226 3.83558C17.3262 4.39317 17.4263 4.98975 17.4171 5.59037L17.417 5.60167C17.417 7.19425 16.7779 8.69082 15.5313 10.3853C14.2776 12.0894 12.4729 13.9114 10.2387 16.1668L10.2378 16.1678L9.40592 17L8.57001 16.1547C6.33556 13.903 4.5297 12.0819 3.27586 10.3793C2.02923 8.68644 1.39031 7.19132 1.39031 5.60167L1.39022 5.59037C1.38109 4.98975 1.48122 4.39317 1.68475 3.83558C1.88828 3.27798 2.19112 2.77057 2.57553 2.34308C2.95994 1.91559 3.41819 1.57661 3.92344 1.34599C4.42817 1.1156 4.96968 0.997993 5.51629 1.00003C6.16756 1.00685 6.80981 1.16786 7.39923 1.47208C7.98913 1.77657 8.51219 2.21732 8.93262 2.7642L9.40368 3.37694L9.87476 2.7642C10.2952 2.21732 10.8182 1.77657 11.4081 1.47208Z"
                      fill="#C4C4C4"
                      fillOpacity="0.4"
                      stroke="#B2B2B2"
                    />
                  </svg>
                </div>
              )}
              <div className={styles.subject}>
                <h2>{v.title}</h2>
              </div>
              <div className={styles.tagsWrap}>
                <div className={`${styles.tag} ${styles.tag1}`}>온라인</div>
                <div className={`${styles.tag} ${styles.tag2}`}>프로그래밍</div>
                <div className={`${styles.tag} ${styles.tag3}`}>3개월</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default StudyPage;
