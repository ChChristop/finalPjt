import React, { useState } from 'react';
import {Form, Modal} from 'react-bootstrap';

function ReplyModal({postData}) {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  
  const [newMsg, setNewMsg] = useState('');
  const onChangeHandler = (e) => {
    console.log(e.target.value);
    setNewMsg(e.target.value);
  };


  return (
    <>
      <button  className='delrepB' onClick={handleShow}>
        수정
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton></Modal.Header>
        <Modal.Body>
          <Form>
              <Form.Label>댓글 수정</Form.Label>
              <Form.Control
                type="text"
                value={newMsg}
                onChange={onChangeHandler}
                autoFocus
              />
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <button className='closed' onClick={handleClose}>
            닫기
          </button>
          <button className='changed' onClick={handleClose}>
            등록
          </button>
        </Modal.Footer>
      </Modal>
    </>
  );
}



export default ReplyModal;