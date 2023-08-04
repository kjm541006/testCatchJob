import axios from "axios";
import React, { useRef } from "react";
import styles from "../../assets/css/study/StudyModal.module.css";

const StudyModal = ({ isOpen, onClose, buttonKey, modalType }) => {
  const modalRef = useRef(null);

  const handleClickOutside = (e) => {
    if (modalRef.current && modalRef.current.contains(e.target)) {
      return;
    }
    onClose();
  };
  const handleSubmit = async () => {
    try {
      const response = await axios.post("http://localhost:8089/임시", { buttonKey });
      if (response.status === 200) {
        alert("신청되었습니다.");
        onClose();
      } else {
        alert("오류가 발생했습니다.");
      }
    } catch (error) {
      console.error("에러 발생", error);
    }
  };

  if (!isOpen) return null;

  return (
    <div className={styles.modal} onClick={handleClickOutside}>
      <div className={styles.modalContent} ref={modalRef}>
        <div className={styles.modalContentWrapper}>
          <h2 className={styles.modalHeader}>
            catch<span style={{ color: "#e2432e" }}>J</span>ob
          </h2>
          {modalType === "participant" && (
            <>
              <h3>프로젝트 지원하기 ({buttonKey})</h3>
              <textarea
                name=""
                id=""
                cols="70"
                rows="10"
                placeholder="지원 사유를 입력하세요. (최대 150자까지 작성 가능)"
                maxLength={150}
                className={styles.modalTextArea}
              ></textarea>
              <div className={styles.modalBtnWrapper}>
                <button onClick={onClose}>취소</button>
                <button onClick={handleSubmit}>지원</button>
              </div>
            </>
          )}
          {modalType === "writer" && (
            <>
              <h3>{buttonKey}</h3>
              <div className={styles.applyListWrapper}>
                <div className={styles.applyList}>
                  <div className={styles.applyLeft}>
                    <div className={styles.applyName}>김주민</div>
                    <div className={styles.applyEmail}>kim@naver.com</div>
                  </div>
                  <div className={styles.applyReason}>지원 사유</div>
                </div>
                <div className={styles.applyList}>
                  <div className={styles.applyLeft}>
                    <div className={styles.applyName}>김주민</div>
                    <div className={styles.applyEmail}>kim@naver.com</div>
                  </div>
                  <div className={styles.applyReason}>지원 사유</div>
                </div>
                <div className={styles.applyList}>
                  <div className={styles.applyLeft}>
                    <div className={styles.applyName}>김주민</div>
                    <div className={styles.applyEmail}>kim@naver.com</div>
                  </div>
                  <div className={styles.applyReason}>지원 사유</div>
                </div>
                <div className={styles.applyList}>
                  <div className={styles.applyLeft}>
                    <div className={styles.applyName}>김주민</div>
                    <div className={styles.applyEmail}>kim@naver.com</div>
                  </div>
                  <div className={styles.applyReason}>지원 사유</div>
                </div>
                <div className={styles.applyList}>
                  <div className={styles.applyLeft}>
                    <div className={styles.applyName}>김주민</div>
                    <div className={styles.applyEmail}>kim@naver.com</div>
                  </div>
                  <div className={styles.applyReason}>지원 사유</div>
                </div>
                <div className={styles.applyList}>
                  <div className={styles.applyLeft}>
                    <div className={styles.applyName}>김주민</div>
                    <div className={styles.applyEmail}>kim@naver.com</div>
                  </div>
                  <div className={styles.applyReason}>지원 사유</div>
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default StudyModal;
