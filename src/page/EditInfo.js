import { Table, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const EditInfo = ({ memData }) => {
  let navigate = useNavigate();
  let [pw, setPw] = useState("");
  let [chkpw, setChkPw] = useState("");
  let [phone, setPhone] = useState("");

  return (
    <div>
      <div className="editCenter">
        <br />
        <h2>
          <strong>회원정보 변경</strong>
        </h2>
        <br />
        <hr />
        <br />

        {memData && (
          <Table striped className="table">
            <tbody>
              <tr className="tr">
                <td className="td1">아이디(e-mail)</td>
                <td className="td2">{memData.memberID}</td>
              </tr>
              <tr className="tr">
                <td className="td1">닉네임</td>
                <td className="td2">{memData.nickname}</td>
              </tr>
              <tr className="tr">
                <td className="td3">비밀번호</td>
                <td className="td2">
                  <input
                    type="password"
                    className="pw"
                    onChange={(e) => {
                      setPw(e.target.value);
                    }}
                    placeholder="변경할 비밀번호"
                  />
                  <br />
                  <input
                    type="password"
                    className="pw"
                    onChange={(e) => {
                      setChkPw(e.target.value);
                    }}
                    placeholder="비밀번호 확인"
                  />
                </td>
              </tr>
              <tr className="tr">
                <td className="td1">휴대전화</td>
                <td className="td2">
                  <input
                    type="text"
                    className="phone"
                    onChange={(e) => {
                      setPhone(e.target.value);
                    }}
                    placeholder={memData.phoneNumber}
                  />
                </td>
              </tr>
            </tbody>
          </Table>
        )}
        <br />
        <br />
        <div className="blind">
          <Button
            className="cnxl"
            variant="dark"
            onClick={() => {
              navigate("/mypage");
            }}
          >
            취소
          </Button>
          {"-----"}
          <Button
            className="ok"
            variant="dark"
            onClick={() => {
              alert("변경이 완료되었습니다.");
              navigate("/mypage");
            }}
          >
            저장
          </Button>
        </div>
      </div>
    </div>
  );
};
export default EditInfo;
