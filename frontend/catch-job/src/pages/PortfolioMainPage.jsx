import { React, useEffect, useState } from "react";
import styles from "../assets/css/PortfolioMain.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faCommentDots, faHeart, faCheck, faPencil } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
// import PortfolioModal from "../../components/PortfolioModal";
import PortfolioModal from "../components/PortfolioModal";
import { useLocation, Link } from "react-router-dom";

const PortfolioMainPage = () => {

  const [data, setData] = useState([]); 
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedItemId, setSelectedItemId] = useState(null);
  const queryParam = new URLSearchParams(useLocation().search);
  const itemFromURL = queryParam.get("boardId");

  useEffect(() => {
    if (itemFromURL) {
      setSelectedItemId(parseInt(itemFromURL));
      setIsModalOpen(true);
    } else {
      setIsModalOpen(false);
    }
  }, [itemFromURL]);

  useEffect(() => {
    axios
      .get("http://43.202.98.45:8089/")
      .then((response) => {
        setData(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.error("데이터 가져오기 에러:", error);
      });
  }, []);

  useEffect(() => {
    console.log(isModalOpen);
  }, [isModalOpen]);

  useEffect(() => {
    axios
      .get("http://43.202.98.45:8089/")
      .then((response) => {
        setData(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.error("데이터 가져오기 에러:", error);
      });
  }, []);

  const handleElementClick = async (board_id) => {
    setSelectedItemId(board_id);
    setIsModalOpen(true);
    try {
      await axios.post(`http://43.202.98.45:8089/portfolio/${board_id}`, { board_id });
      console.log("조회수 증가 성공");
    } catch (error) {
      console.error("error", error); 
    }
  };  
  //조회수 증가 코드

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
            <div key={item.boardId} className={`${styles.element}`} onClick={() => handleElementClick(item.boardId)}>
              <img className={`${styles.img}`} src={item.bCoverFileName} alt="img" />
              <div className={`${styles.info}`}>
                <img className={`${styles.user_img}`} src={item.member.mOriginalFileName} alt="img" />
                <div className={`${styles.info_left}`}>{item.member.name}</div>
                <div className={`${styles.info_right}`}>
                  <FontAwesomeIcon icon={faCommentDots} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.bComment}</span>
                  <FontAwesomeIcon icon={faEye} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.bCnt}</span>
                  <FontAwesomeIcon icon={faHeart} className={`${styles.faIcon}`} />
                  <span className={`${styles.num}`}>{item.bLike}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className={styles.makeProjectBtnWrapper}>
              <Link to={"/portfolio/build"} className={styles.makeProject}>
                <FontAwesomeIcon icon={faPencil} />
                <div>글 쓰기</div>
              </Link>
            </div>
      </div>
      {isModalOpen && <PortfolioModal item={data.find((item) => item.boardId === selectedItemId)} onClose={() => setIsModalOpen(false)} />}
    </div>
  );
};

export default PortfolioMainPage;
