import React, { useState, useRef, useEffect } from "react";
import styles from "../assets/css/PortfolioModal.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComment, faHeart, faPenToSquare, faShare, faTrash } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import ShareModal from "../components/ShareModal";
import { useLocation } from "react-router-dom";

const PortfolioModal = ({ item, onClose }) => {
  const token = localStorage.getItem("token");
  const writerName = localStorage.getItem("name");
  const writerEmail = localStorage.getItem("email");

  const contentCommentRef = useRef(null);
  const [isLiked, setIsLiked] = useState(false);
  const [comment, setComment] = useState("");
  const [commentList, setCommentList] = useState(item.comments || []);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const location = useLocation();
  const [firstModalUrl, setFirstModalUrl] = useState("");

  useEffect(() => {
    // item이 변경될 때마다 URL을 업데이트합니다.
    if (item) {
      const newModalUrl = window.location.origin + location.pathname + "?boardId=" + item.boardId;
      setFirstModalUrl(newModalUrl);
    }
  }, [item, location.pathname]);

  if (!item) {
    return null;
  }

  const handleLike = (event) => {
    event.stopPropagation();
    setIsLiked((prevIsLiked) => !prevIsLiked);
  };

  const handleComment = (event) => {
    event.stopPropagation();
    contentCommentRef.current.scrollIntoView({ behavior: "smooth" });
  };

  const handleShare = (event) => {
    event.stopPropagation();
    setIsModalOpen(true);
  };

  const formatCommentDate = (dateString) => {
    const date = new Date(dateString);
    const formatDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(
      2,
      "0"
    )} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}:${String(date.getSeconds()).padStart(
      2,
      "0"
    )}`;
    return formatDate;
  };

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };

  const submitComment = async () => {
    const response = await axios.post(
      `http://43.202.98.45:8089/portfolio/comment/${item.boardId}`,
      {
        memberName: writerName,
        memberEmail: writerEmail,
        commentContent: comment,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (response.status === 200) {
      const commentDateFromServer = response.data.commentDate;

      console.log("댓글 전송 성공");
      console.log(token);
      console.log(writerName);
      console.log(writerEmail);
      console.log(comment);

      setCommentList([
        ...commentList,
        {
          commentContent: comment,
          commentDate: commentDateFromServer,
          memberName: writerName,
          memberEmail: writerEmail,
        },
      ]);

      setComment("");
    } else {
      console.log("댓글 전송 실패", Error);
    }
  };

  return (
    <>
      <div className={`${styles.modalBackdrop}`} onClick={onClose}>
        <div className={`${styles.buttonSet}`}>
          <button className={`${styles.modalButton}`} style={{ backgroundColor: "#E2432E" }} onClick={handleLike}>
            <FontAwesomeIcon icon={faHeart} className={`${styles.faIcon}`} style={{ color: isLiked ? "#ffb5b5" : "#ffffff" }} />
          </button>
          <div className={`${styles.buttonMent}`}>좋아요</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{ top: "130px", right: "408px" }} onClick={handleComment}>
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faComment} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>댓글</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{ top: "247px", right: "394px" }} onClick={handleShare}>
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faShare} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>공유하기</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{ top: "362px", right: "394px" }}>
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faPenToSquare} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>수정하기</div>
        </div>
        <div className={`${styles.buttonSet}`} style={{ top: "477px", right: "394px" }}>
          <button className={`${styles.modalButton}`}>
            <FontAwesomeIcon icon={faTrash} className={`${styles.faIcon}`} />
          </button>
          <div className={`${styles.buttonMent}`}>삭제하기</div>
        </div>

        <div className={`${styles.modalContent}`} onClick={(e) => e.stopPropagation()}>
          <div className={`${styles.contentInfo}`}>
            <img className={`${styles.user_img}`} src={item.member.mOriginalFileName} alt="img" />
            <div className={`${styles.contentUser}`}>
              <div className={`${styles.contentUser_title}`}>{item.bTitle}</div>
              <div className={`${styles.contentUser_info}`}>
                {item.member.name} ({item.member.email})
              </div>
            </div>
          </div>
          <div className={`${styles.realContent}`}>{item.bContents}</div>
          <div className={`${styles.tagList}`}>
            {item.tags[0] && <div className={`${styles.tagElement}`}>{item.tags[0]}</div>}
            {item.tags[1] && <div className={`${styles.tagElement}`}>{item.tags[1]}</div>}
            {item.tags[2] && <div className={`${styles.tagElement}`}>{item.tags[2]}</div>}
          </div>
          <div className={`${styles.contentFile}`}>
            첨부파일:{" "}
            <a href={item.bFileName} download target="_blank" rel="noopener noreferrer">
              <span className={`${styles.contentFileName}`}>{item.bFileName}</span>
            </a>
          </div>
          <div className={`${styles.contentComment}`} ref={contentCommentRef}>
            <div className={`${styles.comments}`}>Comments</div>
            <textarea
              className={`${styles.commentBox}`}
              placeholder="댓글을 작성하세요.(최대 작성 가능한 글자 수는 100자입니다.)"
              maxlength="100"
              value={comment}
              onChange={handleCommentChange}
            ></textarea>
            <button className={`${styles.commentEnter}`} onClick={submitComment}>
              등록
            </button>
          </div>
          <div className={`${styles.readComment}`}>
            {commentList.map((comment) => (
              <div className={`${styles.readCommentItem}`}>
                <div key={comment.commentDate} className={`${styles.contentUser}`}>
                  <div className={`${styles.contentUser_title}`}>
                    {comment.memberName} ({comment.memberEmail})
                  </div>
                  <div className={`${styles.contentUser_info}`} style={{ color: "#9F9F9F" }}>
                    {formatCommentDate(comment.commentDate)}
                  </div>
                </div>
                <div className={`${styles.commentContent}`}>{comment.commentContent}</div>
                <div className={`${styles.commentBar}`}></div>
              </div>
            ))}
          </div>
        </div>
      </div>
      {isModalOpen && <ShareModal item={item} onClose={() => setIsModalOpen(false)} modalUrl={firstModalUrl} />}
    </>
  );
};

export default PortfolioModal;
