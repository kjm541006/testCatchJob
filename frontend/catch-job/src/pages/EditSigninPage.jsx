import React, { useState } from "react";
import "../assets/css/member/EditSignin.css";

//axios 기능 구현해야함

const EditSigninPage = () => {
  return (
    <div className="body-edit">
      <div className="section-edit">
        <div className="entire-box-edit">
          <h1 className="catchJob-edit">
            catch<span className="red-letter">J</span>ob
          </h1>
          <h3 className="edit-inform">회원정보수정</h3>

          <div className="input-text-edit">이메일</div>
          <input type="text" className="input-box-edit" tabIndex="1" />
          <div className="input-text-edit">이름</div>
          <input type="text" className="input-box-edit" tabIndex="2" />
          <div className="input-text-edit">비밀번호</div>
          <input type="text" className="input-box-edit" tabIndex="3" />

          <div className="input-text-edit">직무</div>
          <div className="choosejob-edit" id="pick-edit">
            <div className="choosejobone-edit">
              <input type="checkbox" className="custom-checkbox-edit" />
              <div className="choosejob-text-edit">웹디자이너</div>
            </div>
            <div className="choosejobone-edit">
              <input type="checkbox" className="custom-checkbox-edit" />
              <div className="choosejob-text-edit">웹퍼블리셔</div>
            </div>
            <div className="choosejobone-edit">
              <input type="checkbox" className="custom-checkbox-edit" />
              <div className="choosejob-text-edit">프론트엔드</div>
            </div>
          </div>
          <div className="choosejob-edit">
            <div className="choosejobone-edit">
              <input type="checkbox" className="custom-checkbox-edit" />
              <div className="choosejob-text-edit">백엔드</div>
            </div>
            <div className="choosejobone-edit">
              <input type="checkbox" className="custom-checkbox-edit" />
              <div className="choosejob-text-edit">PM</div>
            </div>
            <div className="choosejobone-edit">
              <input type="checkbox" className="custom-checkbox-edit" />
              <div className="choosejob-text-edit">기타</div>
            </div>
          </div>

          <div className="input-text-edit">경력 여부</div>
          <div className="choosejob-edit" id="pick-edit">
            <div className="choosejobone-edit" id="carrer-edit">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" />
              <div className="choosejob-text-edit">신입</div>
            </div>
            <div className="choosejobone" id="carrer">
              <input type="radio" className="custom-checkbox-edit" name="job-edit" />
              <div className="choosejob-text-edit">경력</div>
            </div>
          </div>

          <div className="enrollbutton-edit">
            <button className="cancel-edit">취소</button>
            <button className="enroll-edit">등록</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditSigninPage;
