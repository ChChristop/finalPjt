import React from 'react';
import App from './../App.css';
import Button from 'react-bootstrap/Button';
import File from './File';
import Flip from './Flip';

function Square() {
  return (
    <>
      <div>
        <h2 className="my1">레시피 자랑해주세요(가제)</h2>
      </div> 

      <div className="Im">
        <Flip/></div>
    </>
  );
}
export default Square;
