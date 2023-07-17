import axios from "axios";
import React from "react";

const MyPage = () => {
  // const fetchMemberInfo = async () => {
  //   try {
  //     const token = localStorage.getItem("token");
  //     const response = await axios.get("http://43.202.98.45:8089/memberInfo");

  //     // 받아온 회원 정보 처리
  //     const memberInfo = response.data;
  //     console.log(memberInfo);
  //     // 처리 로직 작성
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };

  // // fetchMemberInfo 함수 호출
  // fetchMemberInfo();

  const fetchMemberInfo = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://43.202.98.45:8089/memberInfo", {
        headers: {
          Authorization: token,
        },
        params: {
          // 쿼리 파라미터에 데이터 전달
          email: "asdf",
          pwd: "asdf",
        },
      });

      // 받아온 회원 정보 처리
      const memberInfo = response.data;
      console.log(memberInfo);
      // 처리 로직 작성
    } catch (error) {
      console.error(error);
    }
  };

  // fetchMemberInfo 함수 호출
  fetchMemberInfo();

  return (
    <div>
      memberInfo
      <button onClick={fetchMemberInfo}>정보 가져오기</button>
    </div>
  );
};

export default MyPage;
