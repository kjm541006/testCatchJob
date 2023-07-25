import React, { useState } from "react";
import axios from "axios";

function PostModal({ onPostSubmit, onCancel }) {
  const [loading, setLoading] = useState(false);
  const [postTitle, setPostTitle] = useState("");
  const [postContent, setPostContent] = useState("");
  const [postCategory, setPostCategory] = useState("");

  const handlePostTitleChange = (event) => {
    setPostTitle(event.target.value);
  };

  const handlePostContentChange = (event) => {
    setPostContent(event.target.value);
  };

  const handlePostCategoryChange = (event) => {
    setPostCategory(event.target.value);
  };

  const handleSave = () => {
    setLoading(true);
    if (postTitle.trim() === "" || postContent.trim() === "" || postCategory === "") {
      alert("모든 필드를 입력해주세요.");
      setLoading(false);
      return;
    }

    const newPost = {
      profileImg: "", // Replace with the profile image URL or leave it blank if not required
      cType: postCategory,
      cTitle: postTitle,
      cContents: postContent,
      cLike: 0, // Assuming initial like count is 0
    };

    axios
      .post("http://localhost:3000/api/community", newPost)
      .then((response) => {
        setLoading(false);
        onPostSubmit(response.data);
        setPostCategory("");
        setPostTitle("");
        setPostContent("");
      })
      .catch((error) => {
        setLoading(false);
        console.error("Error submittion post:", error);
      });
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
        <div className="catchJobletter">
          catch<span className="red-letter">J</span>ob
        </div>
        <div>
          <select className="postcategory" value={postCategory} onChange={handlePostCategoryChange}>
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
