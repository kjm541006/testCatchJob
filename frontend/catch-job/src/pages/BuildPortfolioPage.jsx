import React, { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css"; // 테마 스타일 가져오기
import styles from "../assets/css/BuildPortfolio.module.css";
import DetailModal from "../components/DetailModal";
import axios from "axios";

const BuildPortfolioPage = () => {
  const [value, setValue] = useState("");
  const [title, setTitle] = useState("");
  const [uploadedFile, setUploadedFile] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [bCoverFileName, setBCoverFileName] = useState("");
  const [tags, setTags] = useState([]);
  const [prevCover, setPrevCover] = useState("");
  const [prevTags, setPrevTags] = useState([]);
  const [prevCoverURL, setPrevCoverURL] = useState("");

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleChange = (content) => {
    setValue(content);
  };

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    setUploadedFile(file);
  };

  const handleDetailSave = (cover, coverURL, tags) => {
    setBCoverFileName(cover);
    setTags(tags);
    setPrevCover(cover);
    setPrevTags(tags);
    setPrevCoverURL(coverURL);
  };

  const handleSaveContent = async () => {
    const token = localStorage.getItem("token");
    const axiosConfig = {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`,
      },
    };

    const formData = new FormData();
    formData.append("bTitle", title);
    formData.append("bContents", value);
    formData.append("tags", JSON.stringify(tags));
    formData.append("bFileName", uploadedFile);
    formData.append("bCoverFileName", bCoverFileName);

    try {
      const response = await axios.post("http://43.202.98.45:8089/buildportfolio", formData, axiosConfig);
      console.log(response.data);
      console.log("성공")
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const modules = {
    toolbar: [
      [{ header: [1, 2, false] }],
      ["bold", "italic", "underline", "strike", "blockquote"],
      [{ list: "ordered" }, { list: "bullet" }, { indent: "-1" }, { indent: "+1" }],
      ["image"],
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
        <div className={`${styles.realEditor}`} tabIndex="2" style={{ width: "1200px", height: "800px" }}>
          <style>{`.ql-container.ql-snow .ql-editor {font-size: 16px;}`}</style>
          <ReactQuill value={value} onChange={handleChange} modules={modules} theme="snow" className={`${styles.customQuillEditor}`} />
        </div>
        <div className={`${styles.fileName}`}>
          <span>{uploadedFile ? uploadedFile.name : ""}</span>
          {uploadedFile && (
            <span className={`${styles.removeBtn}`} onClick={() => setUploadedFile("")}>
              X
            </span>
          )}
        </div>
      </div>
      <div className={`${styles.addThings}`}>
        <input type="file" id="fileUpload" style={{ display: "none" }} onChange={handleFileUpload} />
        <button className={`${styles.addButtons}`} onClick={() => document.getElementById("fileUpload").click()}>
          파일 업로드
        </button>
        <button to={"/detail"} className={`${styles.addButtons}`} onClick={() => setShowModal(true)}>
          세부 사항 설정
        </button>
        <button className={`${styles.saveContent}`} onClick={handleSaveContent}>
          저<span style={{ marginLeft: "20px" }}></span>장
        </button>
      </div>
      {showModal && (
        <DetailModal
          setShowModal={setShowModal}
          onSave={handleDetailSave}
          prevCover={prevCover}
          prevCoverURL={prevCoverURL}
          prevTags={prevTags}
        />
      )}
    </>
  );
};

export default BuildPortfolioPage;
