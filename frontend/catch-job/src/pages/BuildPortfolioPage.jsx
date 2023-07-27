import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; // 테마 스타일 가져오기
import styles from '../assets/css/BuildPortfolio.module.css';
function MyComponent() {
  const [value, setValue] = useState("");
  const [title, setTitle] = useState('');

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  }

  const handleChange = (content) => {
    setValue(content);
  };

  const modules = {
    toolbar: [
      [{ header: [1, 2, false] }],
      ["bold", "italic", "underline", "strike", "blockquote"],
      [{ list: "ordered" }, { list: "bullet" }, { indent: "-1" }, { indent: "+1" }],
      ["image"]
    ],
  };

  return (
    <div className={`${styles.wrapper}`}>
      <input
        type="text"
        tabIndex="1"
        name="email"
        value={title}
        onChange={handleTitleChange}
        placeholder="제목을 입력하세요"
        className={`${styles.realEditorTitle}`}
      ></input>
      <div className={`${styles.realEditor}`} tabIndex="2" style={{ width: '1200px', height: '800px' }}>
        <style>{`.ql-container.ql-snow .ql-editor {font-size: 16px;}`}</style>
        <ReactQuill
          value={value}
          onChange={handleChange}
          modules={modules}
          theme="snow"
          className={`${styles.customQuillEditor}`}
        />
      </div>
      <div className={`${styles.addThings}`}>
        <button className={`${styles.addButtons}`}>파일 업로드</button>
        <button className={`${styles.addButtons}`}>세부 사항 설정</button>
        <button className={`${styles.saveContent}`}>저<span style={{marginLeft:"20px"}}></span>장</button>
      </div>
    </div>
  );
  
}

export default MyComponent;
