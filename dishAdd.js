import { useState } from 'react';
import axios from 'axios';

function Rank() {
  const [img, setImg] = useState();

  const onUploadImage = (e) => {
    console.log(e.target.files);
    setImg(e.target.files[0]);
  };

  const onPost = async () => {
    const formData = new FormData();
    formData.append('file01', img);
    axios
      .post('http://192.168.0.23:8080/api/dish/add/33', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div className="detailList">
      <input type="file" accept="image/*" onChange={onUploadImage} />
      <button label="이미지 업로드" onClick={onPost}>
        제출
      </button>
    </div>
  );
}

export default Rank;
