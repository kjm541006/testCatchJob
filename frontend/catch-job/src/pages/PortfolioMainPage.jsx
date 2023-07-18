import {React, useEffect, useState} from "react";
import styles from "../assets/css/PortfolioMain.module.css";
import img from "../assets/img/port_img.jpeg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faCommentDots, faHeart, faCheck } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

const PortfolioMainPage = () => {

  const dummyData = [
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    {
      id: 1,
      comments: 10,
      views: 1000,
      likes: 50,
      userId: "cghee0718",
      imageUrl: img,
      userImageUrl: img,
    },
    // 다른 더미 데이터를 추가하고 싶다면 여기에 객체를 추가하면 됩니다.
  ];


  const [data, setData] = useState(dummyData);

  useEffect(() => {
    axios.get("서버_엔드포인트_URL")
      .then(response => {
        setData(response.data); // 서버 응답은  { id: 2, comments: 2, views: 456, likes: 5, infoLeft: "Element 2",  "imageUrl": "이미지_URL_1",  "userImageUrl": "이미지_URL_2", }, 이런 식이여야함
        console.log(response.data);
      })
      .catch(error => {
        console.error("데이터 가져오기 에러:", error);
      });
  }, []);

  return (
    <div className={`${styles.port_wrapper}`}>
      <div className={styles.port_page}>
        <div className={`${styles.port_sort}`}>
          <FontAwesomeIcon icon={faCheck} className={`${styles.port_checkIcon}`} />
          <span className={`${styles.port_topRated} ${styles.port_btn}`}>인기순</span>
          <FontAwesomeIcon icon={faCheck} className={`${styles.port_checkIcon} ${styles.port_invisible}`} />
          <span className={`${styles.port_new}`}>최신순</span>
        </div>
        <div className={`${styles.port_GridView}`}>
          {data.map((item) => (
            <div key={item.id} className={`${styles.element}`}>
              <img className={`${styles.img}`} src={item.imageUrl} alt="img" />
              <div className={`${styles.info}`}>
              <img className={`${styles.user_img}`} src={item.userImageUrl} alt="img" />
                <div className={`${styles.info_left}`}>{item.userId}</div>
                <div className={`${styles.info_right}`}>
                  <FontAwesomeIcon icon={faCommentDots} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.comments}</span>
                  <FontAwesomeIcon icon={faEye} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.views}</span>
                  <FontAwesomeIcon icon={faHeart} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.likes}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default PortfolioMainPage;
