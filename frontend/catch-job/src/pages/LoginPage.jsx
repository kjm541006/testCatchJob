import React, { useState } from "react";
import "../assets/css/Login.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-regular-svg-icons";
import axios from "axios";
import { Link } from "react-router-dom";

const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = () => {
    const userData = {
      email: email,
      password: password,
    };

    axios.post("서버의 로그인 API 엔드포인트 URL", userData)
      .then(response => {
        const token = response.data.token; 

        localStorage.setItem("token", token);
        console.log(response.data); 
      })
      .catch(error => {
        console.error(error); 
      });
  };

  return (
    <div className="body-login">
      <div className="section-login">
        <div className="entire-box">
          <h1 className="catchJob">
            catch<span className="red-letter">J</span>ob
          </h1>
          <div className="input-text">이메일</div>
          <input type="text" className="input-box" tabIndex="1" value={email}
            onChange={e => setEmail(e.target.value)} />
          <div className="input-text" tabIndex="2">
            비밀번호
          </div>
          <div className="input-container">
            <input type={showPassword ? "text" : "password"} className="input-box"  value={password}
              onChange={e => setPassword(e.target.value)}/>
            <div className="eye-icon" onClick={togglePasswordVisibility}>
              <FontAwesomeIcon icon={showPassword ? faEye : faEyeSlash} />
            </div>
          </div>

          <button className="login-button" onClick={handleLogin}>
            <div className="login-text">로그인</div>
          </button>

          <div className="entire-text">SNS로 간편하게 시작하기</div>

          <div className="social-buttons">
            <button className="kakao-button"></button>
            <button className="google-button"></button>
          </div>

          <div className="sign-in">
            <div className="entire-text">아직 회원이 아니세요?</div>
            <Link to="/social-signin" className="sign-in-now">회원가입 하기</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
