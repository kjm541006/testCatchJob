import logo from "./logo.svg";
import "./reset.css";
import "./App.css";
import Header from "./components/header/Header";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Footer from "./components/footer/Footer";
import Temp from "./pages/Temp";

function App() {
  return (
    <div className="App">
      {/* 리액트 라우터 돔  URL경로 표시용 */}
      <BrowserRouter>
        <Routes>
          <Route></Route>
        </Routes>
      </BrowserRouter>
      {/* 헤더 컴포넌트 */}
      <Header />
      {/* 아래는 푸터 컴포넌트 확인용 임시 컴포넌트 입니다. */}
      <Temp />
      <Footer />
    </div>
  );
}

export default App;
