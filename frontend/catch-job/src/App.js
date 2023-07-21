import "./App.css";
import "./reset.css";
import Header from "./components/header/Header";
import { Navigate, Route, Routes } from "react-router-dom";
import Footer from "./components/footer/Footer";
import LoginPage from "./pages/LoginPage";
import SocialSigninPage from "./pages/SocialSigninPage";
import BasicSigninPage from "./pages/BasicSigninPage";
import EditSigninPage from "./pages/EditSigninPage";
import PortfolioMainPage from "./pages/PortfolioMainPage";
import CommunityRoutes from "./pages/community/CommunityPage";
import StudyPage from "./pages/study&project/StudyPage";
import BuildStudyPage from "./pages/study&project/BuildStudyPage";
// import GoogleLoginButton from "./components/GoogleLoginButton";
// import { GoogleOAuthProvider } from "@react-oauth/google";
// import GoogleLoginButton_long from "./components/GoogleLoginButton_long";
import { useDispatch, useSelector } from "react-redux";
import MyPage from "./pages/MyPage";
import { selectUserLoggedOut, setCredentials, setTokenFromLocalStorage, userLoggedOut } from "./redux/login";
import { logOut, selectEmail, selectLoggedIn, selectName } from "./redux/login";
import LoginRoute from "./components/LoginRoute";
import { useEffect } from "react";
import LoginAlertPage from "./pages/LoginAlertPage";

function App() {
  const dispatch = useDispatch();
  const name = localStorage.getItem("name");
  const email = localStorage.getItem("email");
  const savedToken = localStorage.getItem("token");
  const isLoggedIn = useSelector(selectLoggedIn);
  const userLoggedOut = useSelector(selectUserLoggedOut);

  useEffect(() => {
    console.log(isLoggedIn);
    if (savedToken) {
      dispatch(setTokenFromLocalStorage({ name, email, token: savedToken }));
    }
  }, [isLoggedIn, savedToken, dispatch, email, name]);

  return (
    <div className="App">
      <Header />
      <Routes>
        <Route element={<Footer />}>
          {/* <Route path="/userInfo/:userId" element={<UserInfo />} /> */}
          <Route path="/" element={<PortfolioMainPage />} />
          {/* <Route path="/recruit" element={<Recruit />} /> */}
          <Route path="/study" element={<StudyPage />} />
          <Route path="/study/build" element={savedToken != null ? <BuildStudyPage /> : <LoginAlertPage />} />
          {/* <Route path="/community/*" element={<CommunityRoutes />} /> */}
        </Route>
        <Route path="/join" element={<SocialSigninPage />} />
        <Route path="/signin" element={<BasicSigninPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/mypage" element={savedToken != null ? <MyPage /> : <LoginAlertPage />} />
        <Route path="/edit" element={<EditSigninPage />} />
        {/* <Route path="/userInfo/:userId" element={<UserInfo />} /> */}
        <Route path="/" element={<PortfolioMainPage />} />
        {/* <Route path="/recruit" element={<Recruit />} /> */}
        {/* <Route path="/study/*" element={<StudyRoutes />} /> */}
        <Route path="/community/*" element={<CommunityRoutes />} />
      </Routes>
    </div>
  );
}

export default App;
