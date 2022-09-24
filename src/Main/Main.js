import React from 'react';
import App from './../App.css';
import Nav from 'react-bootstrap/Nav';
import Playground from './Playground';
import ImageSlider from './imageSlider';
import Calendar from './Calendar';


function Main({realData}) {

  return (
    <>
    {/* <div className='my'><h4>냉장Go 파먹Go</h4></div> */}
    <div className='b1'><Calendar/></div>
    <div className="b2"><ImageSlider realData={realData} /></div>
    <div className="b3"><Playground realData={realData} /></div>
    </>
  );
}

export default Main;
