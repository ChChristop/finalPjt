import { useEffect, useState } from "react";

export default function Parallax() {
  const [position, setPosition] = useState(0);

  function onScroll() {
    setPosition(window.scrollY);
  }
  useEffect(() => {
    window.addEventListener("scroll", onScroll); // 이벤트 생성
    return () => {
      window.removeEventListener("scroll", onScroll); // 이벤트 제거
    };
  }, []);

  return (
    <div className="wrapper">
      <img
        src="./image/요리모습.jpg"
        className="item item_snow"
        style={{
          transform: `translateX(${position / 4}px)`,
          // backgroundPositionY: position / 4,
        }}
      />
      <p
        className="desc"
        style={{
          transform: `translateY(${position / 4}px)`,
        }}
      >
        좋은 음식은 좋은 대화로 끝이 난다.
      </p>{" "}
      .
      <div className="야채사진">
        <img
          src="./image/야채사진.png"
          className="item item_snow"
          style={{
            transform: `translateY(${position / 2}px)`,
          }}
        />
      </div>
      <div className="야채사진">
        <img
          src="./image/야채사진.png"
          className="item item_snow"
          style={{
            transform: `translateY(${position / 2}px)`,
          }}
        />{" "}
        <img className="밥묵자" src="./image/밥묵자.jpg"></img>
      </div>
    </div>
  );
}

{
  /* <img
        src="./image/고양이.jpg"
        className="item"
        alt=""
        style={{
          transform: `translateY(${position / 2}px)`,
        }}
      />
      <img
        src="./image/요리모습.jpg"
        className="item item_snow"
        alt=""
        style={{
          transform: `translateY(${position / 4}px)`,
        }}
      /> */
}

{
  /* <div
        className="bg bg2"
        style={{
          backgroundPositionY: position / -3,
        }}
      >
        <div>Happy New Year</div>
      </div> */
}

{
  /* <p
        className="desc"
        style={{
          transform: `translateX(${-position}px)`,
        }}
      >
        왼쪽으로 지나갑니다 왼쪽으로 지나갑니다 왼쪽으로 지나갑니다 왼쪽으로
        지나갑니다왼쪽으로 지나갑니다 왼쪽으로 지나갑니다 왼쪽으로 지나갑니다
        왼쪽으로 지나갑니다왼쪽으로 지나갑니다 왼쪽으로 지나갑니다 왼쪽으로
        지나갑니다 왼쪽으로 지나갑니다왼쪽으로 지나갑니다 왼쪽으로 지나갑니다
        왼쪽으로 지나갑니다 왼쪽으로 지나갑니다왼쪽으로 지나갑니다 왼쪽으로
        지나갑니다 왼쪽으로 지나갑니다 왼쪽으로 지나갑니다왼쪽으로 지나갑니다
        왼쪽으로 지나갑니다 왼쪽으로 지나갑니다 왼쪽으로 지나갑니다
      </p> */
}
{
  /* <p
        className="desc2"
        style={{
          transform: `translateX(${position}px)`,
        }}
      >
        오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다오른쪽으로 지나갑니다오른쪽으로 지나갑니다오른쪽으로
        지나갑니다
      </p> */
}

{
  /* <p
        className="desc3"
        style={{
          opacity: (position - 700) / 50,
        }}
      >
        <strong>감사합니다</strong>
      </p> */
}

{
  /* <p
        className="desc3"
        style={{
          opacity: (position - 830) / 50,
        }}
      >
        Lorem ipsum dolor sit amet
      </p> */
}

{
  /* <p
        className="desc3"
        style={{
          opacity: (position - 960) / 50,
        }}
      >
        Excepteur sint occaecat
      </p> */
}

{
  /* <p
        className="desc3"
        style={{
          opacity: (position - 1090) / 50,
        }}
      >
        깜박이는 글씨입니다
      </p> */
}
