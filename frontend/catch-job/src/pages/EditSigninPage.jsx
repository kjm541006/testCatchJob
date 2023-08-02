import React, { useState, useEffect } from 'react';
import EditSignin from "../assets/css/member/EditSignin.css";
import axios from 'axios';
import MyPage from './MyPage';
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faCommentDots, faHeart, faCheck, faPencil } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const EditSigninPage = () => {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [selectedJobs, setSelectedJobs] = useState("");
  const [selectedCarrers, setSelectedCarrers] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [imageFileName, setImageFileName] = useState("");
  const navigate = useNavigate();
  
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
        setImageFile(response.data.mOriginalFileName);
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

  const handleUpdateClick = async () => {
    const password = prompt("비밀번호를 입력해 주세요.");
  
    if (password) {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      };
      try {
        const response = await axios.post('http://43.202.98.45:8089/memberPwd', { pwd: password }, config);
        console.log(response.data);

        if (response.data === "비밀번호 일치") {
          navigate("/realmypage");
        }
      } catch (error) {
        if (error.response && error.response.status >= 400){
          alert("비밀번호가 일치하지 않습니다. 다시 작성해주세요.");
          handleUpdateClick();
        } else {
          console.error('Error:', error);
        }
      }
    } else {
      alert("비밀번호를 입력해야 회원 정보를 수정할 수 있습니다.");
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
            <img src={imageFile ? `${imageFile}`: "/profile.png"} alt="" className="profile-image"/>
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
            <button className="enroll-edit" onClick={handleUpdateClick}>수정하기</button>

          </div>
        </div>
      </div>
    </div>
  );
};

export default EditSigninPage;
