import logo from "./logo.svg";
import "./reset.css";
import "./App.css";
// import Header from "./components/header/Header";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Card from "./pages/community/Card.jsx";

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
      {/* <Header /> */}
      <Card />
    </div>
  );
}

export default App;
