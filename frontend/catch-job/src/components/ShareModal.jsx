import React from 'react';
import styles from "../assets/css/ShareModal.module.css";

const ShareModal = ({ item,onClose }) => {

  if (!item) {
    return null;
  }

  return (
    <div className={`${styles.modalBackdrop}`}>
      <div className={`${styles.modalContent}`}>
        <button className={`${styles.closeButton}`} onClick={onClose}>x</button>
        <h1 className={`${styles.catch}`}>
            catch<span className={`${styles.catchJ}`}>J</span>ob
        </h1>
        <div className={`${styles.title}`}>프로젝트 공유하기</div>
        <div className={`${styles.contentInfo}`}>
          <img className={`${styles.user_img}`} src={item.member.mOriginalFileName} alt="img" />
          <div className={`${styles.contentUser}`}>
            <div className={`${styles.contentUser_title}`}>{item.bTitle}</div>
            <div className={`${styles.contentUser_info}`}>{item.member.name} ({item.member.email})</div>
          </div>
        </div>
      </div>
    </div>
  ); 
};

export default ShareModal;