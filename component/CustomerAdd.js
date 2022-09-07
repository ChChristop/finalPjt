import React, { useState } from "react";
import { post } from "axios";

function CosTomerAdd() {
  constructor();
  {
    constructor(props);
    {
      super(props);
      this.state = {
        file: null,
        dish_name: "",
        mnum: "",
        data: "",
        imgList: null,
      };
    }

    handleFormSubmit = (e) => {
      e.preventDefault();
      this.addCustomer().then((response) => {
        console.log(response.data);
      });
    };

    addCustomer = () => {
      const url = "http://192.168.0.23:8080/api/dish/add";
      const formData = new FormData();
      formData.append("사진", this.state.file);
      formData.append("음식 이름", this.state.dish_name);
      formData.append("레시피", this.state.recipe);
      const config = {
        headers: {
          "content-type": "multipart/form-data",
        },
      };
      return post(url, formData, config);
    };

    return (
      <form onSubmit={this.handleFormSubmit}>
        <h1>게시글 작성</h1>
        제목:{" "}
        <input
          type="text"
          name="dish_name"
          file={this.state.file}
          value={this.state.dish_name}
          onChange={this.handleFileChange}
        />{" "}
        <br />
        메인 사진:{" "}
        <input
          type="file"
          name="mainIMG"
          file={this.state.file}
          value={this.state.mainIMG}
          onChange={this.handleValueChange}
        />
        설명:{" "}
        <input
          type="text"
          name="recipe"
          value={this.state.recipe}
          onChange={this.handleValueChange}
        />
        <br />
        사진(1):{" "}
        <input
          type="file"
          name="imgList"
          file={this.state.file}
          value={this.state.imgList}
          onChange={this.handleValueChange}
        />
        설명(1):{" "}
        <input
          type="text"
          name="recipe"
          value={this.state.recipe}
          onChange={this.handleValueChange}
        />
        <br />
        사진(2):{" "}
        <input
          type="file"
          name="imgList"
          file={this.state.file}
          value={this.state.imgList}
          onChange={this.handleValueChange}
        />
        설명(2):{" "}
        <input
          type="text"
          name="recipe"
          value={this.state.recipe}
          onChange={this.handleValueChange}
        />
        <br />
        사진(3):{" "}
        <input
          type="file"
          name="imgList"
          file={this.state.file}
          value={this.state.imgList}
          onChange={this.handleValueChange}
        />
        설명(3):{" "}
        <input
          type="text"
          name="recipe"
          value={this.state.recipe}
          onChange={this.handleValueChange}
        />
        <button type="submit">추가하기</button>
      </form>
    );
  }
}

export default CosTomerAdd;

{
  /* <h2>{props.dish_num}</h2>
      <img src={props.imgList} alt="profile"></img>
      <h3>{props.dish_name}</h3>
      <h3>{props.mnum}</h3>
      <p>{props.data}</p> */
}
