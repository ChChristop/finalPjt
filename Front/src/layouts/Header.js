import React, { useEffect, useState } from "react";
import { Nav, Navbar, Button, Container } from "react-bootstrap";
import SignUpModal from "../modals/SignUpModal";
import SignInModal from "../modals/SignInModal";
import axios from "axios";
import "./../App.css";
import { useAuthActions } from "../store/authSlice";
import { store } from "../store/store";
import { useSelector } from "react-redux";
import { useDispatch } from "react";

//SignUp 회원가입 SignIn 로그인

const Header = () => {
  const [SignUp, setSignUp] = useState(false);
  const [Login, setSignIn] = useState(false);
  const [idLogin, setLogin] = useState(false);

  const userInfo = useAuthActions();
  const auths = useSelector((state) => state.auth.user);
  //--------------------------------------------------------------

  //-------------------------------------------------------------------
  const test = (userno) => {
    setLogin(userno);
  };

  const onClickHandler = () => {
    const ids = auths.memberID ? auths.memberID : auths.adminID;
    //const userID = auths.memberID ? auths.memberID : auths.adminID;
    console.log("auths.authorization: " + auths.authorization);
    console.log("auths.refreshtoken: " + auths.refreshtoken);
    console.log(ids);
    axios
      .get(`http://3.38.19.221:8081/api/logout/${ids}`, {
        headers: {
          // authorization:
          //   "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMzMiLCJyb2xlIjoiW01FTUJFUl0iLCJtdW0iOjMwNiwiYWRtaW5DaGVjayI6ImZhbHNlIiwiZXhwIjoxNjY0MTU5ODU0LCJtZW1iZXJJRCI6IjMzMyJ9.67ffVMOyxmRyXXNkNuV_jTIRKVP9pSxtVOJ--ngokff7KAjPyBD9EBi2Nt1iY_fUq9DPz-SLevYlawWVKlGp3w",
          authorization: auths.authorization,
          refreshtoken: auths.refreshtoken,
          "Content-Type": "application/json; charset=utf-8",
        },
        responseType: "json",
        responseEncoding: "utf8",
      })
      .then((response) => {
        console.log(response.data);
        alert("로그아웃 되었습니다");
        window.location.replace("/");
        userInfo.logout();
        setLogin(false);
      });
  };

  // useEffect(() => {
  //   console.log(idLogin);
  // });

  return (
    <div>
      <SignInModal
        show={Login}
        test={(userno) => test(userno)}
        onHide={() => setSignIn(false)}
      />
      <SignUpModal show={SignUp} onHide={() => setSignUp(false)} />
      <header>
        <Navbar className="logoutCSS">
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav>
              {!idLogin ? (
                <>
                  <Nav.Link>
                    <Button
                      variant="info"
                      onClick={() => {
                        setSignIn(true);
                      }}
                    >
                      로그인
                    </Button>
                  </Nav.Link>
                  <Nav.Link>
                    <Button variant="warning" onClick={() => setSignUp(true)}>
                      회원가입
                    </Button>
                  </Nav.Link>
                </>
              ) : (
                <Nav.Link>
                  <span>
                    {auths.nickname} {auths.adminID} {""}님
                  </span>
                  <Button className="logout" onClick={onClickHandler}>
                    로그아웃
                  </Button>
                </Nav.Link>
              )}
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </header>
    </div>
  );
};

export default Header;
