import React, { useState, useEffect } from 'react';
import EditSignin from "../assets/css/member/EditSignin.css";
import axios from 'axios';
import MyPage from './MyPage';
import { Link } from "react-router-dom";

const EditSigninPage = () => {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [selectedJobs, setSelectedJobs] = useState("");
  const [selectedCarrers, setSelectedCarrers] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [imageFileName, setImageFileName] = useState("");
  
  useEffect(() => {
    async function fetchData() {
      const token = localStorage.getItem("token");
      const axiosConfig = {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      };
      try {
        const response = await axios.get("http://43.202.98.45:8089/memberInfo", axiosConfig);
        setEmail(response.data.email);
        setName(response.data.name);
        setSelectedJobs(response.data.job);
        setSelectedCarrers(response.data.hasCareer);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }
    fetchData();
  }, []);

  const handleImageChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setImageFile(e.target.files[0]);
    }
  };

  const uploadImage = async () => {
    const formData = new FormData();
    formData.append("image", imageFile);
    
    // 이미지를 업로드하고 저장하는 로직 구현
    try {
      const response = await axios.post("your-api-endpoint", formData);
      if (response.data && response.data.fileName) {
        setImageFileName(response.data.fileName);
      }
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <div className="body-edit">
    <div className="section-edit">
      <div className="entire-box-edit">
        <h1 className="catchJob-edit">
          catch<span className="red-letter">J</span>ob
        </h1>
        <h3 className="edit-inform">회원정보조회</h3>

        <div className="profile-image-container">
            <label htmlFor="profile-image-input">
            <img src={'/profile.png'} alt="" className="profile-image" />
              <input
                type="file"
                id="profile-image-input"
                accept="image/*"
                onChange={handleImageChange}
                className="profile-image-input"
              />
          </label>
        </div>
        
        <div className="input-text-edit">이메일</div>
        <input type="text" className="input-box-edit" tabIndex="1" value={email} readOnly style={{color:'#807d7d'}}/>
        <div className="input-text-edit">이름</div>
        <input type="text" className="input-box-edit" tabIndex="2" value={name} readOnly style={{color:'#807d7d'}}/>

        <div className="input-text-edit">직무</div>
          <div className="choosejob-edit" id="pick-edit">
            <div className="choosejobone-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" checked={selectedJobs === "웹디자이너"} />
              <div className="choosejob-text-edit">웹디자이너</div>
            </div>
            <div className="choosejobone-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" checked={selectedJobs === "웹퍼블리셔"}  />
              <div className="choosejob-text-edit">웹퍼블리셔</div>
            </div>
            <div className="choosejobone-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" checked={selectedJobs === "프론트엔드"}  />
              <div className="choosejob-text-edit">프론트엔드</div>
            </div>
          </div>
          <div className="choosejob-edit">
          <div className="choosejobone-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" checked={selectedJobs === "백엔드"} />
              <div className="choosejob-text-edit">백엔드</div>
            </div>
            <div className="choosejobone-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" checked={selectedJobs === "PM"} />
              <div className="choosejob-text-edit">PM</div>
            </div>
            <div className="choosejobone-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" checked={selectedJobs === "기타"} />
              <div className="choosejob-text-edit">기타</div>
            </div>
          </div>

          <div className="input-text-edit">경력 여부</div>
          <div className="choosejob-edit" id="pick-edit">
            <div className="choosejobone-edit" id="carrer-edit">
              <input type="radio" className="custom-checkbox-edit"  name="career-edit" checked={selectedCarrers === "신입"}  />
              <div className="choosejob-text-edit">신입</div>
            </div>
            <div className="choosejobone" id="carrer">
              <input type="radio" className="custom-checkbox-edit" name="career-edit" checked={selectedCarrers === "경력"} />
              <div className="choosejob-text-edit">경력</div>
            </div>
          </div>

          <div className="enrollbutton-edit">
            <Link to={"/"} className="cancel-edit">메인으로</Link>
            <button className="enroll-edit">수정하기</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditSigninPage;
