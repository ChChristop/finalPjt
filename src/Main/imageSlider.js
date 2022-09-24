import { useState, useEffect } from 'react';
import './imageSlider.css';
import dataSlider from './imageSliderData';
import axios from 'axios';

export default function ImageSlider({realdata}) {
  
  useEffect(() => {
    axios.get(`http://192.168.0.23:8080/api/main/ing`)

 .then((realdata) => console.log(realdata));
  },[]);
  const [sliderIndex, setSliderIndex] = useState(1);
   
  const moveDot = (index) => {
    setSliderIndex(index);
  };
  const nextSlide = () => {
    if (sliderIndex !== dataSlider.length) {
      setSliderIndex((sliderIndex) => sliderIndex + 1);
    } else if (sliderIndex === dataSlider.length) {
      setSliderIndex(1);
    }
  };

  const prevSlide = () => {
    if (sliderIndex !== 1) {
      setSliderIndex((sliderIndex) => sliderIndex - 1);
    } else if (sliderIndex === 1) {
      setSliderIndex(dataSlider.length);
    }
  };

  useEffect(() => {});
  setTimeout(() => {
    moveDot(sliderIndex);
  }, 400);
  return (
    <div className="slider-container">
      {dataSlider &&
        dataSlider.map((img, id) => {
          return (
            <div
              key={img.id}
              className={sliderIndex === id + 1 ? 'slide active-anim' : 'slide'}
            >
              <img src={img.url} />
            </div>
          );
        })}

      <button className="slider-btn prev" onClick={prevSlide}>
        {' '}
        <i className="arrow left"></i>
      </button>
      <button className="slider-btn next" onClick={nextSlide}>
        {' '}
        <i className="arrow right"></i>
      </button>

      <div className="dot-container">
        {dataSlider.map((item, idx) => {
          return (
            <div
              key={idx}
              onClick={() => moveDot(idx + 1)}
              className={sliderIndex === idx + 1 ? 'dot active' : 'dot'}
            ></div>
          );
        })}
      </div>
    </div>
  );
}
