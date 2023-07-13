import React, { useState } from "react";

function PostModal({ onPostSubmit, onCancel }) {
  const [postTitle, setPostTitle] = useState("");
  const [postContent, setPostContent] = useState("");
  const [postCategory, setPostCategory] = useState("");

  const handlePostTitleChange = (event) => {
    setPostTitle(event.target.value);
  };

  const handlePostContentChange = (event) => {
    setPostContent(event.target.value);
    console.log(postContent);
  };

  const handlePostCategoryChange = (event) => {
    setPostCategory(event.target.value);
  };

  const handleSave = () => {
    if (postTitle.trim() === "") {
      alert("제목을 입력해주세요.");
      return;
    }

    if (postContent.trim() === "") {
      alert("내용을 입력해주세요.");
      return;
    }

    if (postCategory === "") {
      alert("카테고리를 선택해주세요.");
      return;
    }

    const newPost = { id: Date.now(), title: postTitle, content: postContent, category: postCategory };
    onPostSubmit(newPost);
    setPostCategory("");
    setPostTitle("");
    setPostContent("");
  };

  const handleCancel = () => {
    onCancel();
    setPostCategory("");
    setPostTitle("");
    setPostContent("");
  };

  return (
    <div className="overlay">
      <div className="postmodal">
        <div className="catchJob">
          catch<span className="red-letter">J</span>ob
        </div>
        <div>
          <select className="postcategory" value={postCategory} onChange={handlePostCategoryChange} required>
            <option selected disabled hidden value="">
              카테고리
            </option>
            <option value="기술 질문">기술 질문</option>
            <option value="취업 고민">취업 고민</option>
            <option value="기타">기타</option>
          </select>
        </div>
        <div className="postTitle">
          <input placeholder="제목" type="text" className="postTitleform" value={postTitle} onChange={handlePostTitleChange} />
        </div>
        <textarea className="postText" value={postContent} onChange={handlePostContentChange} />
        <div className="postButton">
          <button className="postButton1" onClick={handleCancel}>
            취소
          </button>
          <button className="postButton2" onClick={handleSave}>
            등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default PostModal;
