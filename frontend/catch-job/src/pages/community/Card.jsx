import React, { useState } from "react";
import "./Card.css";
import Modal from "./Modal";
import PostModal from "./PostModal";
import "./PostModal.css";

function Card(props) {
  const [commentModalOpen, setCommentModalOpen] = useState(false);
  const [comments, setComments] = useState([]);
  const [comment, setComment] = useState("");
  const [postContent, setPostContent] = useState("");
  const [expanded, setExpanded] = useState(false);
  const [postModalOpen, setPostModalOpen] = useState(false);
  const [showComments, setShowComments] = useState(false);

  const toggleCommentModal = () => {
    setCommentModalOpen(!commentModalOpen);
    setShowComments(!showComments);
  };

  const handleCommentChange = (event) => {
    setComment(event.target.value);
  };

  const handleSubmitComment = () => {
    const newComment = { id: Date.now(), content: comment };
    setComments((prevComments) => [...prevComments, newComment]);
    setComment("");
  };

  const toggleExpanded = () => {
    setExpanded(!expanded);
  };

  const handleSubmitPost = () => {};

  const togglePostModal = () => {
    setPostModalOpen(!postModalOpen);
  };

  const renderComments = () => {
    if (comments.length === 0 || !showComments) {
      return null;
    }

    return (
      <div className="commentsContainer">
        {comments.map((comment) => (
          <div key={comment.id} className="commentment">
            <div className="commentmentuser">{comment.id}</div>
            <div className="commentmentment">
              <p>{comment.content}</p>
            </div>
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className="communityCenter">
      <div className="communitySection">
        <div className="communityCard">
          <div className="userSection">
            <div>
              <img src="" alt="프로필" />
            </div>
            <div>유저닉네임</div>
            <div>시간</div>
          </div>

          <div className="cardContentsComponents">
            <div className="cardContentsComponents_Title">
              <span>카테고리</span>
              <h3>[Unity3D] 포스트 아포칼립스 세계관 게임 제작</h3>
            </div>

            <div>
              <div className="cardContentsComponents_TextArea">
                <p>글내용글내용글내용글내용글내용글내용</p>
                {expanded && (
                  <div>
                    <p>추가적인 내용...</p>
                  </div>
                )}
              </div>
              <div className="cardContentsComponents_morebutton">
                <span className="moreButton" onClick={toggleExpanded}>
                  {expanded ? "닫기" : "펼치기"}
                </span>
              </div>
            </div>
          </div>
          <div>
            <div className="cardContentsComponents_bottom">
              <button className="love">
                <img src="" alt="버튼" />
              </button>

              <span className="ment" onClick={toggleCommentModal}>
                댓글
              </span>
            </div>
            {commentModalOpen && (
              <Modal
                onCommentSubmit={handleSubmitComment}
                onCancel={toggleCommentModal}
                comment={comment}
                onCommentChange={handleCommentChange}
              />
            )}
          </div>
          <div className="commentContainer">{renderComments()}</div>
        </div>
      </div>

      <div className="작성하기">
        <div>
          <button className="writeButton" onClick={togglePostModal}>
            글작성하기
          </button>

          <div className="ccategory">
            <span>전체</span>
            <span>기술 질문</span>
            <span>취업 고민</span>
            <span>기타</span>
          </div>
        </div>
      </div>

      {postModalOpen && <PostModal onClose={togglePostModal} />}
    </div>
  );
}

export default Card;
