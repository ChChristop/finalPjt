import React from "react";
import ReactDOM from "react-dom";


function File() {
  const uploadedImage = React.useRef(null);
  const imageUploader = React.useRef(null);

  const handleImageUpload = e => {
    const [file] = e.target.files;
    if (file) {
      const reader = new FileReader();
      const { current } = uploadedImage;
      current.file = file;
      reader.onload = e => {
        current.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        padding: '20px'

        
      }}
    >
      <input
        type="file"
        accept="image/*"
        onChange={handleImageUpload}
        ref={imageUploader}
        style={{
          display: "none"
        }}
      />
      <div
        style={{
          height: "300px",
          width: "300px",
          border: "2px dashed black"
          
        }}
        onClick={() => imageUploader.current.click()}
      >
        <img
          ref={uploadedImage}
          alt="img"
          style={{
            width: "100%",
            height: "100%"
            // position: "absolute"
          }}
        />
      </div>
     
    </div>
  );
}

const rootElement = document.getElementById("root");
ReactDOM.render(<File />, rootElement);

export default File;