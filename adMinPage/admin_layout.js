import { BrowserRouter as Router, Route, Link, Routes } from "react-router-dom";
import { ListGroup, Col } from "react-bootstrap";
import { Nav } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Board_Page from "./board-Page";
import UserList from "./userlist";
import NewBoard from "./newboard";

const Admin_Layout = () => {
  let navigate = useNavigate();

  return (
    <div>
      <div className="floatL">
        <div className="info">
          <ListGroup>
            <Col>
              <Nav className="flex-column">
                <ListGroup.Item>
                  <Nav.Link
                    onClick={() => {
                      navigate("/admin-Page/BoardPage/");
                    }}
                  >
                    <strong className="black">게시물관리</strong>
                  </Nav.Link>
                </ListGroup.Item>

                <ListGroup.Item>
                  <Nav.Link
                    onClick={() => {
                      navigate("/admin-Page/NewBoard/");
                    }}
                  >
                    <strong className="black">게시글 작성</strong>
                  </Nav.Link>
                </ListGroup.Item>
                <br />
                <ListGroup.Item>
                  <Nav.Link
                    onClick={() => {
                      navigate("/admin-Page/UserList/");
                    }}
                  >
                    <strong className="black">유저 관리</strong>
                  </Nav.Link>
                </ListGroup.Item>
              </Nav>
            </Col>
          </ListGroup>
        </div>
      </div>
      <Routes>
        <Route path="UserList" element={<UserList />} />
        <Route path="NewBoard" element={<NewBoard />} />
        <Route path="BoardPage" element={<Board_Page />} />
      </Routes>
    </div>
  );
};
export default Admin_Layout;
