import React, { useState } from "react";
import "../css/Login.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash   } from "@fortawesome/free-regular-svg-icons";

const Login = () => {
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <body>
      <section>
        <div className="entire-box">
          <h1 className="catchJob">
            catch<span className="red-letter">J</span>ob
          </h1>
          <div className="input-text">이메일</div>
          <input type="text" className="input-box" tabIndex="1"/>
          <div className="input-text" tabIndex="2">비밀번호</div>
          <div className="input-container">
            <input
              type={showPassword ? "text" : "password"}
              className="input-box"
            />
            <div className="eye-icon" onClick={togglePasswordVisibility}>
              <FontAwesomeIcon icon={showPassword ? faEye : faEyeSlash} />
            </div>
          </div>

          <button className="login-button">
          <div className="login-text">로그인</div>
          </button>

          <div className="entire-text">SNS로 간편하게 시작하기</div>

          <div className="social-buttons">
            <button className="kakao-button"></button>
            <button className="google-button"></button>
          </div>

          <div className="sign-in">
            <div className="entire-text">아직 회원이 아니세요?</div>
            <button className="sign-in-now">회원가입 하기</button>
          </div>
        </div>      
      </section>
    </body>
  );
};

export default Login;
