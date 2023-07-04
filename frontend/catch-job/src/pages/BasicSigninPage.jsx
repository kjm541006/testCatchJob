import React from 'react';
import "../assets/css/BasicSignin.css"

const BasicSigninPage = () => {
  return (
    <body>
    <section>
      <div className="entire-box-basic">
        <h1 className="catchJob-basic">
          catch<span className="red-letter">J</span>ob
        </h1>
        
        <div className="input-text-basic">이메일</div>
        <input type="text" className="input-box-basic" tabIndex="1"/>
        <div className="input-text-basic">이름</div>
        <input type="text" className="input-box-basic" tabIndex="2"/>
        <div className="input-text-basic">비밀번호</div>
        <input type="text" className="input-box-basic" tabIndex="3"/>
        <div className="input-text-basic">비밀번호 확인</div>
        <input type="text" className="input-box-basic" tabIndex="4"/>

        <div className="input-text-basic">직무</div>
          <div className="choosejob">
            <div className="choosejobone">
              <input type="checkbox" className="custom-checkbox"/>
              <div className="choosejob-text-basic">웹디자이너</div>
            </div>
            <div className="choosejobone">
              <input type="checkbox" className="custom-checkbox"/>
              <div className="choosejob-text-basic">웹퍼블리셔</div>
            </div>
            <div className="choosejobone">
              <input type="checkbox" className="custom-checkbox"/>
              <div className="choosejob-text-basic">프론트엔드</div>
            </div>
          </div>
          <div className="choosejob">
            <div className="choosejobone">
              <input type="checkbox" className="custom-checkbox"/>
              <div className="choosejob-text-basic">백엔드</div>
            </div>
            <div className="choosejobone">
              <input type="checkbox" className="custom-checkbox"/>
              <div className="choosejob-text-basic">PM</div>
            </div>
            <div className="choosejobone">
              <input type="checkbox" className="custom-checkbox"/>
              <div className="choosejob-text-basic">기타</div>
            </div>
          </div>

        <div className="input-text-basic">경력 여부</div>
        <div className="choosejob">
            <div className="choosejobone">
              <input type="radio" className="custom-checkbox" id="exp" name="job"/>
              <div className="choosejob-text-basic">신입</div>
            </div>
            <div className="choosejobone">
              <input type="radio" className="custom-checkbox" id="exp" name="job"/>
              <div className="choosejob-text-basic">경력</div>
            </div>
        </div>

        <div className="log-in-basic">
            <div className="entire-text-basic">이미 계정이 있으신가요?</div>
            <button className="log-in-now-basic">로그인 하기</button>
          </div>
      </div>      
     
    </section>
  </body>
  );
};

export default BasicSigninPage;