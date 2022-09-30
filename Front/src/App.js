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
import Rankinglayout from "./rankingpage/Ranking";
import { store } from "./store/store";
import Parallax from "./component/Parallax";

function App() {
  let navigate = useNavigate();
  let dispatch = useDispatch();
  let [mod, setmod] = useState("normal");
  //---------------------------------------------------------

  //-------------------------------------------------------------
  const userInfo = useSelector((state) => state.auth.user);

  let [realData, setRealData] = useState([]);
  useEffect(() => {
    axios
      .get("http://3.38.19.221:8081/api/dish/get")
      .then((response) => {
        // console.log(response.data)
        setRealData(response.data);
      })
      .catch(() => {
        console.log("실패");
      });
  }, [userInfo.mnum]);
  ///// console.log(realData);
  // console.log(JSON.stringify(realData));

  return (
    <div className="MYDIV">
      <div className="app">
        <Header />
        <button
          className="제목"
          onClick={() => {
            navigate("/");
          }}
        >
          {" "}
          냉장<img src="./image/go.png"></img> 파먹
          <img src="./image/go.png"></img>
        </button>

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
                  <strong className="rmfTl">Home🏠</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/detail");
                  }}
                >
                  <strong className="rmfTl">레시피🍴</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/card");
                  }}
                >
                  <strong className="rmfTl">광장🍀</strong>
                </Nav.Link>
                <Nav.Link
                  className="var"
                  onClick={() => {
                    navigate("/Rankinglayout/RanKingPage");
                  }}
                >
                  <strong className="rmfTl">랭킹🏆</strong>
                </Nav.Link>
                {userInfo?.mnum && (
                  <Nav.Link
                    className="var"
                    onClick={() => {
                      navigate("/mypage");
                    }}
                  >
                    <strong className="rmfTl">마이페이지👤</strong>
                  </Nav.Link>
                )}
                {userInfo?.anum && (
                  <Nav.Link
                    className="var"
                    onClick={() => {
                      navigate("/admin-Page/BoardPage/");
                    }}
                  >
                    <strong className="rmfTl">관리자 페이지 🛠️</strong>
                  </Nav.Link>
                )}
              </Nav>
            </div>
          </Container>
        </Navbar>
      </div>
      <Routes className="Routes">
        <Route path="/" element={<Main />} />
        <Route path="/main" element={<Cardfilp realData={realData} />} />
        <Route path="/detail" element={<List realData={realData} />} />
        {/* <Route path="/square" element={<Square realData={realData} />} /> */}
        <Route path="/card" element={<Square realData={realData} />} />
        <Route path="/detail/:id" element={<Detail />} />
        <Route path="/Rankinglayout/*" element={<Rankinglayout />} />
        <Route path="/mypage/*" element={<Mypage />} />
        <Route path="/mylike" element={<Mylike />} />
        <Route path="/editinfo" element={<EditInfo />} />
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
