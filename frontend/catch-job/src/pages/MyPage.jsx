import axios from "axios";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { selectCurrentToken } from "../redux/login";

const MyPage = () => {
  const dispatch = useDispatch();
  const userEmail = localStorage.getItem("email");

  const currentToken = useSelector(selectCurrentToken);
  console.log(currentToken);

  const fetchMemberInfo = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://43.202.98.45:8089/memberInfo", {
        headers: {
          Authorization: token,
        },
        params: {
          // 쿼리 파라미터에 데이터 전달
          email: userEmail,
          pwd: "asdf",
        },
      });

      // 받아온 회원 정보 처리
      const memberInfo = response.data;
      console.log(memberInfo);

      // Store user's name and email
      // const { name, email } = memberInfo;
      // dispatch(setCredentials({ name, email, accessToken: token }));
      // 처리 로직 작성
    } catch (error) {
      console.error(error);
    }
  };

  // fetchMemberInfo 함수 호출
  useEffect(() => {
    fetchMemberInfo();
  }, []);
  // fetchMemberInfo();

  return (
    <div>
      memberInfo
      {/* <button onClick={fetchMemberInfo}>정보 가져오기</button> */}
    </div>
  );
};

export default MyPage;
