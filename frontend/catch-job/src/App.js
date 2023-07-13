import "./App.css";
import "./reset.css";
import Header from "./components/header/Header";
import { Route, Routes } from "react-router-dom";
import Footer from "./components/footer/Footer";
import LoginPage from "./pages/LoginPage";
import HeaderFile from "./pages/HeaderFile";
import SocialSigninPage from "./pages/SocialSigninPage";
import BasicSigninPage from "./pages/BasicSigninPage";
import EditSigninPage from "./pages/EditSigninPage";
import Portfolio from "./pages/Portfolio";
import CommunityRoutes from "./pages/community/CommunityPage";
import StudyPage from "./pages/StudyPage";
import GoogleLoginButton from "./components/GoogleLoginButton";
import { GoogleOAuthProvider } from "@react-oauth/google";
import GoogleLoginButton_long from "./components/GoogleLoginButton_long";

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route element={<Footer />}>
          {/* <Route path="/userInfo/:userId" element={<UserInfo />} /> */}
          <Route path="/" element={<Portfolio />} />
          {/* <Route path="/recruit" element={<Recruit />} /> */}
          <Route path="/study/*" element={<StudyPage />} />
          {/* <Route path="/community/*" element={<CommunityRoutes />} /> */}
        </Route>
        <Route path="/join" element={<SocialSigninPage />} />
        <Route path="/signin" element={<BasicSigninPage />} />
        <Route path="/login" element={<LoginPage />} />
        {/* <Route path="/userInfo/:userId" element={<UserInfo />} /> */}
        <Route path="/" element={<Portfolio />} />
        {/* <Route path="/recruit" element={<Recruit />} /> */}
        {/* <Route path="/study/*" element={<StudyRoutes />} /> */}
        <Route path="/community/*" element={<CommunityRoutes />} />
      </Routes>
    </div>
  );
}

export default App;
