import React from "react";
import { BrowserRouter, Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import "./header.css";

const Header = () => {
  return (
    <BrowserRouter>
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
            <div className="joinAndLogin">
              <Link to="/login" className="link login">
                로그인
              </Link>
              <Link to="/join" className="link join">
                회원가입
              </Link>
            </div>
            {/* 로그인 했을 경우 */}
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default Header;
