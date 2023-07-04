import React from 'react';
import "../assets/css/SocialSignin.css";


const SocialSigninPage = () => {
  return (
    <body>
    <section>
      <div className="entire-box-social">
        <h1 className="catchJob-social">
          catch<span className="red-letter">J</span>ob
        </h1>
        
        <div className="hi-text">반가워요 👋</div>

        <div className="social-buttons-social">
            <button className= "button" id ="kakao-button">
              <div className="button-icon-kakao"></div>
              <div className="button-text">카카오로 3초만에 가입하기</div>
            </button>
            <button className= "button" id ="google-button">
              <div className="button-icon-google"></div>
              <div className="button-text">구글 계정으로 가입하기</div>
            </button>
            <button className= "button" id ="basic-button">
              <div className="button-text">이메일로 가입하기</div>
            </button>
        </div>

        <div className="log-in">
            <div className="entire-text-social">이미 계정이 있으신가요?</div>
            <button className="log-in-now">로그인 하기</button>
          </div>
      </div>      
    </section>
  </body>
  );
};

export default SocialSigninPage;