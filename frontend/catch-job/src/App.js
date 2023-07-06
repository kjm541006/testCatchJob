import "./App.css";
import Header from "./components/header/Header";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Footer from "./components/footer/Footer";
import Temp from "./pages/Temp";
import Login from "./pages/LoginPage";
import HeaderFile from "./pages/HeaderFile";
import SocialSigninPage from "./pages/SocialSigninPage";
import BasicSigninPage from "./pages/BasicSigninPage";
import EditSigninPage from './pages/EditSigninPage';
// import "./reset.css";
// import Header from "./components/header/Header";
// import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <HeaderFile/>
      <BasicSigninPage/>
      {/* 리액트 라우터 돔  URL경로 표시용 */}
      {/* <BrowserRouter>
        <Routes>
          <Route></Route>
        </Routes>
      </BrowserRouter> */}
      {/* 헤더 컴포넌트 */}
      <Header />
      {/* 아래는 푸터 컴포넌트 확인용 임시 컴포넌트 입니다. */}
      <Temp />
      <Footer />
      {/* <Header /> */}
    </div>
  );
}

export default App;
