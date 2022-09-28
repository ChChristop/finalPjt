import React, { useState, useEffect } from 'react';
import ReactQuill from 'react-quill';
import './Board.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-quill/dist/quill.snow.css';
import 'react-quill/dist/quill.bubble.css';

export default function App() {
  const [value, setValue] = useState('');
  const [showDraftAlert, toggleDraftAlert] = useState(false);
  const [draftAlertMessage, setDraftAlertMessage] = useState('');
  const [responseRecieved, setResponseRecieved] = useState(true);
  const [contentPublished, setContentPublished] = useState(false);

  useEffect(() => {
    setValue(JSON.parse(localStorage.getItem('content')) || '');
    console.log(value);
  }, [value]);

  const onEditorChange = (value) => {
    setValue(value);
    localStorage.setItem('content', JSON.stringify(value));
  };

  const handleSaveDraftClick = () => {
    if (value.length === 0) {
      toggleDraftAlert(true);
      setDraftAlertMessage(`Nothing to save!`);
    } else {
      toggleDraftAlert(true);
      setDraftAlertMessage(`Content saved!`);
    }
  };

  const handlePublishClick = () => {
    setResponseRecieved(false);

    fetch(' ', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        content: value,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.message === 'SUCCESS') {
          console.log('POSTED');
          setResponseRecieved(true);
          setContentPublished(true);
        }
        console.log(data);
      });
  };

  const handleClearClick = () => {
    setValue('');
    localStorage.removeItem('content');
  };

  const modules = {
    toolbar: [
      [{ header: [1, 2, false] }],
      ['bold', 'italic', 'underline', 'strike', 'blockquote'],
      [
        { list: 'ordered' },
        { list: 'bullet' },
        { indent: '-1' },
        { indent: '+1' },
      ],
      ['link', 'image'],
      ['clean'],
    ],
  };

  const formats = [
    'header',
    'bold',
    'italic',
    'underline',
    'strike',
    'blockquote',
    'list',
    'bullet',
    'indent',
    'link',
    'image',
  ];

  return (
    <div className="App">
      <h1 className="title text-dark">나의 먹킷등록</h1>

      <div className="editor-actions mb-4 ml-auto">
        <button
          onClick={handleClearClick}
          className="btn btn-outline-info btn-sm"
        >
          삭제
        </button>
        <button
          onClick={handleSaveDraftClick}
          className="btn btn-outline-info ml-2 btn-sm"
        >
          저장
        </button>
        <button
          onClick={handlePublishClick}
          className={`btn btn-info ml-2 btn-sm`}
        >
          {!responseRecieved && (
            <span className="spinner-border spinner-border-sm" role="status" />
          )}

          {responseRecieved && 'Publish'}
        </button>
      </div>

      {showDraftAlert && (
        <div className="alert alert-info">{draftAlertMessage}</div>
      )}

      {contentPublished && (
        <div className="alert alert-info">나만의 먹킷</div>
      )}

      <form>
        <div className="mb-3">
          <label htmlFor="title" className="form-label text-dark">
            음식명
          </label>
          <input
            className="form-control"
            type="text"
            placeholder="Post title goes here, make it a great one!"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="title" className="form-label text-dark">
           10줄 맛평가해주세요!
          </label>
          <textarea
            className="form-control"
            type="text"
            maxLength="200"
            rows="2"
            placeholder="Write an awesome description here!"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="title" className="form-label">
           사진 및 게시물 입력
          </label>
          <ReactQuill
            className="textarea"
            theme="snow"
            value={value}
            onChange={onEditorChange}
            modules={modules}
            formats={formats}
            placeholder="Start writing something awesome!"
          />
        </div>
      </form>
    </div>
  );
}
