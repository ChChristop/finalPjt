import "./App.css";
import { Nav, Container, Navbar } from "react-bootstrap";
import { Routes, Route, useNavigate } from "react-router-dom";
import Detail from "./page/Detail";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import Mypage from "./page/Mypage";
import Mylike from "./page/Mylike";
import EditInfo from "./page/EditInfo";
import List from "./page/List";
import Main from "./Main/Main";
import Square from "./Square/Square";
import Header from "./layouts/Header";
import axios from "axios";
import Admin_Layout from "./adMinPage/admin_layout";
import RanKingPage from "./rankingpage/LanKingPage";
import RanKingDish from "./rankingpage/useranking";
import Cardfilp from "./Square/Cardflip";
// import AteBoard from " ./Square/AteBoard";
import Rankinglayout from "./rankingpage/Ranking";

function App() {
  let navigate = useNavigate();
  let dispatch = useDispatch();
  let recipedata = useSelector((state) => state.tmpdata);
  let [mod, setmod] = useState("normal");

  let [realData, setRealData] = useState([]);
  useEffect(() => {
    axios
      .get("http://192.168.0.23:8080/api/dish/get")
      .then((response) => {
        // console.log(response.data)
        setRealData(response.data);
      })
      .catch(() => {
        console.log("실패");
      });
  }, []);
  ///// console.log(realData);
  // console.log(JSON.stringify(realData));

  return (
    <div>
      <div className="login">
        <Header />
      </div>
      <div className="app">
        <h1
          className="name"
          onClick={() => {
            navigate("/");
          }}
        >
          냉장🅶🅾 파먹🅶🅾
        </h1>
        <Navbar>
          <Container>
            <div className="menubar">
              <Nav>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/");
                  }}
                >
                  <strong>Home🏠</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/detail");
                  }}
                >
                  <strong>레시피🍴</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/card");
                  }}
                >
                  <strong>광장🍀</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/Rankinglayout");
                  }}
                >
                  <strong>랭킹🏆</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/mypage");
                  }}
                >
                  <strong>마이페이지👤</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/admin-Page");
                  }}
                >
                  <strong>관리자 페이지 🛠️</strong>
                </Nav.Link>
              </Nav>
            </div>
          </Container>
        </Navbar>
      </div>
      <Routes>
        <Route path="/" element={<Main realData={realData} />} />
        <Route path="/main" element={<Cardfilp realData={realData} />} />
        <Route path="/detail" element={<List realData={realData} />} />
        <Route path="/square" element={<Square realData={realData} />} />
        <Route path="/card" element={<Square realData={realData} />} />
        <Route path="/detail/:id" element={<Detail />} />
        <Route path="/Rankinglayout/*" element={<Rankinglayout />} />
        <Route path="/mypage/*" element={<Mypage realData={realData} />} />
        <Route path="/mylike" element={<Mylike realData={realData} />} />
        <Route path="/editinfo" element={<EditInfo realData={realData} />} />
        <Route path="/admin-Page/*" element={<Admin_Layout />} />
        {/* <Route path="/ateBoard" element={<AteBoard realData={realData} />} />
        <Route path="/atepage" element={<Atepage realData={realData} />} /> */}
      </Routes>
      {mod === "user" ? (
        <Nav.Link
          className="var"
          onClick={() => {
            navigate("/mypage");
          }}
        >
          <strong>마이페이지👤</strong>
        </Nav.Link>
      ) : null}
    </div>
  );
}

export default App;
