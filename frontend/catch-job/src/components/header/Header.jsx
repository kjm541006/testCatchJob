import React, { useState } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import "./header.css";
import axios from "axios";

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(true);

  const toggleLogin = () => {
    axios.post("http://43.202.98.45:8089/logout") 
      .then((response) => {
        if (response.status === 200) {
          localStorage.removeItem("token");
          setIsLoggedIn(false);
        } else {
         
        }
      })
      .catch((error) => {
        console.error("로그아웃 에러:", error);
      });
  };

  return (
    <>
      <div className="header">
        <div className="header-nav">
          <div className="logo">
            <Link to={"/"} className="link">
              catch<span id="logo-j">J</span>ob
            </Link>
          </div>
          <div className="nav">
            <ul className="nav-lists">
              <Link to={"/"} className="link">
                <li className="nav-list">포트폴리오</li>
              </Link>
              <Link to={"/recruit"} className="link">
                <li className="nav-list">채용</li>
              </Link>
              <Link to={"/study"} className="link">
                <li className="nav-list">스터디</li>
              </Link>
              <Link to={"/community"} className="link">
                <li className="nav-list">커뮤니티</li>
              </Link>
            </ul>
          </div>
          <form action="/search" method="post">
            <div className="search-bar">
              <input type="text" placeholder="검색어를 입력하세요." className="search-box" required name="search" />
              {/* <FontAwesomeIcon icon="fa-solid fa-magnifying-glass" /> */}
              <button type="submit" className="submit-btn">
                <FontAwesomeIcon icon={faMagnifyingGlass} className="search-bar-icon" />
              </button>
            </div>
          </form>
          <div className="login-wrap">
            {/* 로그인 안했을 경우 */}
            {!isLoggedIn && (
              <div className="joinAndLogin">
                <Link to="/login" className="link login">
                  로그인
                </Link>
                <Link to="/join" className="link join">
                  회원가입
                </Link>
              </div>
            )}
            {/* 로그인 했을 경우 */}
            {isLoggedIn && (
              <div className="header-user-info">
                <span className="header-username">김주민 님</span>
                <div className="header-logout-btn" onClick={toggleLogin}>
                  로그아웃
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
      <div className="header-height"></div>
    </>
  );
};

export default Header;
