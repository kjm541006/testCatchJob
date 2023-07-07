import React from "react";

function PostModal({ onSave, onCancel, postContent, setPostContent }) {
  const handleSave = () => {
    onSave();
  };

  const handleCancel = () => {
    onCancel();
  };

  return (
    <div className="postmodal">
      <div>catchJob</div>
      <div>
        <select className="postcategory">
          <option value="기술 질문">기술 질문</option>
          <option value="취업 고민">취업 고민</option>
          <option value="기타">기타</option>
        </select>
      </div>
      <div className="postTitle">
        <input type="text" />
      </div>
      <textarea className="postText" value={postContent} onChange={(e) => setPostContent(e.target.value)} />
      <div className="postButton">
        <button onClick={handleCancel}>Cancel</button>
        <button onClick={handleSave}>Save</button>
      </div>
    </div>
  );
}

export default PostModal;
