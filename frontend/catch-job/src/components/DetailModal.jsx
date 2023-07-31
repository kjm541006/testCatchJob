import React, {useState} from 'react';
import styles from '../assets/css/DetailModal.module.css';

const DetailModal = ({ setShowModal,onSave }) => {

  const [uploadedFile, setUploadedFile] = useState('');
  const [tags, setTags] = useState([]);
  const [currentTag, setCurrentTag] = useState('');
  const [bCoverFileName, setBCoverFileName] = useState("");

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    const fileURL = URL.createObjectURL(file);
    setUploadedFile(fileURL);
    setBCoverFileName(file.name);
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' || e.key === 'Tab') {
      e.preventDefault();
      if (currentTag.trim() && tags.length < 3) {
        setTags([...tags, currentTag.trim()]);
        setCurrentTag('');
      }
    }
  };

  const handleInputChange = (e) => {
    setCurrentTag(e.target.value);
  };

  const removeTag = (indexToRemove) => {
    setTags(tags.filter((tag, index) => index !== indexToRemove));
  };

  const saveButtonHandler = () => {
    console.log("bCoverFileName:", bCoverFileName); // 커버 파일 이름 확인
    console.log("tags:", tags); // 태그 확인
    onSave(bCoverFileName, tags);
  };
  


  return (
    <div className={`${styles.modalBackdrop}`}  onClick={() => setShowModal(false)}>
      <div className={`${styles.modalContent}`}  onClick={(e) => e.stopPropagation()}>
        <div className={`${styles.modalTitle}`}>커버</div>
        <div className={`${styles.modalCover}`}   style={{ backgroundImage: `url(${uploadedFile})` }}></div>
        <div className={`${styles.modalCoverDiv}`}>
          <div className={`${styles.modalCoverMent}`}>화면 상의 커버 사이즈는 350X250입니다.</div>
          <input type="file" id="CoverUpload" style={{ display: 'none' }} onChange={handleFileUpload}/>
          <button className={`${styles.modalCoverFile}`} onClick={() => document.getElementById("CoverUpload").click()}>커버 업로드</button>
        </div>
        <div className={`${styles.modalTitle}`}>태그</div>
        <input  type="text" placeholder="Tab, Enter로 구분하여 태그를 작성하세요." className={`${styles.modalTag}`} value={currentTag} onChange={handleInputChange} onKeyDown={handleKeyDown}></input>
        <div className={`${styles.tags}`}>
          {tags.map((tag, index) => (
          <span key={index} className={`${styles.tagElement}`}>{tag}<span className={`${styles.removeBtn}`} onClick={() => removeTag(index)}>×</span></span>))}
        </div> 
        <div className={`${styles.saveContent}`}  onClick={saveButtonHandler}>저<span style={{marginLeft:"20px"}}></span>장</div>    
      </div>
    </div>
  );
};

export default DetailModal;