import React, { useState } from "react";
import { Nav, Navbar, Button, Container } from "react-bootstrap";
import SignUpModal from "../modals/SignUpModal";
import SignInModal from "../modals/SignInModal";
import axios from "axios";
import { propTypes } from "react-bootstrap/esm/Image";
import { useSelector } from "react-redux";
import { selectUser } from "../modals/userSlice";
//import { useCookies } from "react-cookie";

//SignUp 회원가입 SignIn 로그인
const Header = () => {
  const [SignUp, setSignUp] = useState(false);
  const [Login, setSignIn] = useState(false);
  const [idLogin, setLogin] = useState(false);

  const test = (prop) => {
    setLogin(prop);
  };

  console.log(Login);
  const onClickHandler = () => {
    axios.get("http://192.168.0.10:8080/api/logout/333").then((response) => {
      console.log(response.data);
      if (response.data.success) {
        // propTypes.history.push("/");
      } else {
        alert("로그아웃 실패!");
        console.log(response.data);
      }
    });
  };
  if (idLogin == false) {
    return (
      <div>
        <SignInModal
          show={Login}
          test={(prop) => test(prop)}
          onHide={() => setSignIn(false)}
        />
        <SignUpModal show={SignUp} onHide={() => setSignUp(false)} />
        <header>
          <Navbar>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav>
                <Nav.Link>
                  <Button variant="info" onClick={() => setSignIn(true)}>
                    로그인
                  </Button>
                </Nav.Link>
                <Nav.Link>
                  <Button variant="warning" onClick={() => setSignUp(true)}>
                    회원가입
                  </Button>
                </Nav.Link>
              </Nav>
            </Navbar.Collapse>
          </Navbar>
        </header>
      </div>
    );
  } else {
    return (
      <div>
        <Nav.Link>
          <Button className="logout" onClick={onClickHandler}>
            로그아웃
          </Button>
        </Nav.Link>
      </div>
    );
  }
};

export default Header;

/*
 return (
    <div>
      <SignUpModal show={SignUp} onHide={() => setSignUp(false)} />
      <SignInModal show={Login} onHide={() => setSignIn(false)} />
      <header>
        <Navbar>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav>
              <Nav.Link>
                <Button variant="info" onClick={() => setSignIn(true)}>
                  로그인
                </Button>
              </Nav.Link>
              <Nav.Link>
                <Button variant="warning" onClick={() => setSignUp(true)}>
                  회원가입
                </Button>
              </Nav.Link>
              <Nav.Link></Nav.Link>
              <Nav.Link>
                <Button className="logout" onClick={onClickHandler}>
                  로그아웃
                </Button>
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </header>
    </div>
  );
*/
