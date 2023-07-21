import React, { useEffect } from "react";
import "../assets/css/PortfolioMain.module.css";
import { useNavigate } from "react-router-dom";

const PortfolioMainPage = () => {
  // 아래는 react-router-dom 용 코드입니다 유지시켜주세요.
  const navigate = useNavigate();
  useEffect(() => {
    navigate("/");
  }, [navigate]);

  return <div className="temp">Portfolio</div>;
};

export default PortfolioMainPage;
