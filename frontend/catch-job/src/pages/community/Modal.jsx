import React from "react";

function Modal({ onCommentSubmit, onCancel, comment, onCommentChange }) {
  const handleSubmit = () => {
    onCommentSubmit();
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
          style={{ height: 21 }}
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
