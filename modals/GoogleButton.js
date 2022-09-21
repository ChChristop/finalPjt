import GoogleLogin from "react-google-login";

function GoogleButton() {
  const onSuccess = (response) => {
    console.log(response);
  };
  const onFailure = (error) => {
    console.log(error);
  };

  return (
    <div>
      <GoogleLogin
        clientId="338950793796-fsuh7oog818lj6lvs2ds4suorcgoa61e.apps.googleusercontent.com"
        buttonText="구글 로그인"
        onSuccess={onSuccess}
        onFailure={onFailure}
        cookiePolicy={"single_host_origin"}
      ></GoogleLogin>
    </div>
  );
}

export default GoogleButton;
