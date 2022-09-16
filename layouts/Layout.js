import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import { BrowserRouter as Router, Route, Link, Routes } from "react-router-dom";
import Main from "../pages/Main";
import Admin from "../pages/admin";
import { Container, Button } from "react-bootstrap";

const Layout = () => {
  return (
    <>
      <Header />
      <Container>
        <Router>
          <Link to="/">
            <Button variant="outline-dark">Home</Button>
          </Link>
          <Link to="/admin">
            <Button variant="dark">관리자페이지</Button>
          </Link>
          <Routes>
            <Route exact path="/" element={Main()} />
          </Routes>
          <Routes>
            <Route exact path="/admin" element={Admin()} />
          </Routes>
        </Router>

        <hr />
      </Container>
      <Footer />
    </>
  );
};

export default Layout;
