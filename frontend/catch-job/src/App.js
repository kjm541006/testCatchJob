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
import Google from "./pages/Google";
import { GoogleOAuthProvider } from '@react-oauth/google'

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/join" element={<SocialSigninPage />} />
        <Route path="/login" element={<LoginPage />} />
        {/* <Route path="/userInfo/:userId" element={<UserInfo />} /> */}
        <Route path="/" element={<Portfolio />} />
        {/* <Route path="/recruit" element={<Recruit />} /> */}
        {/* <Route path="/study/*" element={<StudyRoutes />} /> */}
        {/* <Route path="/community/*" element={<CommunityRoutes />} /> */}
        <Route path="/social-signin" element={<SocialSigninPage/>} />
      </Routes>
      <Google/>
      <Footer />
    </div>
  );
}

export default App;
