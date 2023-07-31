import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; // 테마 스타일 가져오기
import styles from '../assets/css/BuildPortfolio.module.css';
import DetailModal from "../components/DetailModal";
import axios from 'axios';

const BuildPortfolioPage = () => {
  const [value, setValue] = useState("");
  const [title, setTitle] = useState("");
  const [uploadedFile, setUploadedFile] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [bCoverFileName, setBCoverFileName] = useState("");
  const [tags, setTags] = useState([]);
  
  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  }

  const handleChange = (content) => {
    setValue(content);
  };

  const handleFileUpload = (e) => {
    const file = e.target.files[0]; 
    setUploadedFile(file.name); 
  };
  
  const handleDetailSave = (cover, tags) => {
    setBCoverFileName(cover);
    setTags(tags);
    console.log("메인bCoverFileName:", bCoverFileName); // 커버 파일 이름 확인
    console.log("메인tags:", tags); // 태그 확인
  };

  const handleSaveContent = async () => {
    const token = localStorage.getItem("token");
    console.log(token);
  
    const axiosConfig = {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    };
  
    const board = {
      bTitle: title,
      bContents: value,
      tags: tags,
    };
  
    const data = {
      board: board,
      bFileName: uploadedFile,
      bCoverFileName: bCoverFileName,
    };

    console.log(board,data.bFileName,bCoverFileName);
  
    try {
      const response = await axios.post('http://43.202.98.45:8089/buildportfolio', data, axiosConfig);
      console.log(response.data);
    } catch (error) {
      console.error('Error:', error);
    }
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
    <>
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
      <div className={`${styles.fileName}`}><span>{uploadedFile}</span>{uploadedFile && <span className={`${styles.removeBtn}`} onClick={() => setUploadedFile('')}>X</span>}</div>
    </div>
    <div className={`${styles.addThings}`}>
      <input
          type="file"
          id="fileUpload"
          style={{ display: 'none' }}
          onChange={handleFileUpload}
        />
        <button className={`${styles.addButtons}`}  onClick={() => document.getElementById("fileUpload").click()}>파일 업로드</button>
        <button to={"/detail"} className={`${styles.addButtons}`}  onClick={() => setShowModal(true)}>세부 사항 설정</button>
        <button className={`${styles.saveContent}`} onClick={handleSaveContent}>저<span style={{marginLeft:"20px"}}></span>장</button>      
        </div>
    {showModal && <DetailModal setShowModal={setShowModal}  onSave={handleDetailSave}/>}      
    </>
  );
  
}

export default BuildPortfolioPage;
