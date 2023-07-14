import axios from "axios";
import React, { useState } from "react";

function Modal({ postId, onCommentSubmit, onCancel, comment, onCommentChange }) {
  const [loading, setLoading] = useState(false);

  const handleSubmit = () => {
    setLoading(true);
    axios
      .post(`/api/comments/${postId}`, { content: comment })
      .then((response) => {
        setLoading(false);
        onCommentSubmit();
      })
      .catch((error) => {
        setLoading(false);
        console.error("Error submitting comment", error);
      });
  };

  const handleCancel = () => {
    onCancel();
  };

  return (
    <div className="modal">
      <div className="mentment">
        <textarea
          className="mentmentment"
          maxLength={1000}
          placeholder="댓글을 입력해주세요."
          style={{ height: 20 }}
          value={comment}
          onChange={onCommentChange}
        />
      </div>
      <div style={{ display: "flex", justifyContent: "flex-end" }}>
        <button onClick={handleSubmit}>댓글 등록</button>
        <button onClick={handleCancel}>취소</button>
      </div>
    </div>
  );
}

export default Modal;
