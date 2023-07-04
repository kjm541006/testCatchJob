import "./App.css";
import Login from "./pages/LoginPage";
import HeaderFile from "./pages/HeaderFile";
import SocialSigninPage from "./pages/SocialSigninPage";
import BasicSigninPage from "./pages/BasicSigninPage";
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
      {/* <Header /> */}
    </div>
  );
}

export default App;
