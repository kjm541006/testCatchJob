import React, { useState } from 'react';
import styles from "../assets/css/PortfolioModal.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faComment, faHeart, faPenToSquare, faShare, faTrash } from "@fortawesome/free-solid-svg-icons";
import img from "../assets/img/port_img.jpeg";

const PortfolioModal = ({ item,onClose }) => {

  const [isLiked, setIsLiked] = useState(false);

  if (!item) {
    return null;
  }

  const handleLike = (event) => {
    event.stopPropagation();
    setIsLiked((prevIsLiked) => !prevIsLiked);
  };

  return (
    <div className={`${styles.modalBackdrop}`} onClick={onClose}>
        <div className={`${styles.buttonSet}`}>
          <button className={`${styles.modalButton}`} style={{backgroundColor:"#E2432E"}} onClick={handleLike}>
            <FontAwesomeIcon icon={faHeart} className={`${styles.faIcon}`}   style={{color: isLiked ? "#ffb5b5" : "#ffffff"}}/>
          </button>
          <div className={`${styles.buttonMent}`} >좋아요</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{top:"130px", right:"408px"}} >
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faComment} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>댓글</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{top:"247px", right:"394px"}} >
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faShare} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>공유하기</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{top:"362px", right:"394px"}} >
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faPenToSquare} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>수정하기</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{top:"477px", right:"394px"}} >
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faTrash} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>삭제하기</div>
        </div>

      <div className={`${styles.modalContent}`}>
        <div className={`${styles.contentInfo}`}>
          <img className={`${styles.user_img}`} src={item.member.mOriginalFileName} alt="img" />
          <div className={`${styles.contentUser}`}>
            <div className={`${styles.contentUser_title}`}>{item.bTitle}</div>
            <div className={`${styles.contentUser_info}`}>{item.member.name} ({item.member.email})</div>
          </div>
        </div>
        <div className={`${styles.realContent}`}>{item.bContents}</div>
        <div className={`${styles.tagList}`}>
          {item.tags[0] && (<div className={`${styles.tagElement}`}>{item.tags[0].tagName}</div>)}
          {item.tags[1] && (<div className={`${styles.tagElement}`}>{item.tags[1].tagName}</div>)}
          {item.tags[2] && (<div className={`${styles.tagElement}`}>{item.tags[2].tagName}</div>)}
        </div>
        
      </div>
    </div>
  );
};

export default PortfolioModal;
