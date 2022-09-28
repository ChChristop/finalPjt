// import { useRef } from "react";
// import useScript from "./useScript";

// export default function GoogleLogin({ onGoogleSignIn, text = "signin_with" }) {
//   const googleSignInButton = useRef(null);
//   const client_id =
//     "338950793796-p7i763am926898jrjf495c1f3evlblb5.apps.googleusercontent.com";

//   useScript("https://accounts.google.com/gsi/client", () => {
//     window.google.accounts.id.initialize({
//       //client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID,
//       client_id,
//       callback: onGoogleSignIn,
//     });
//     window.google.accounts.id.renderButton(
//       googleSignInButton.current,
//       { theme: "filled_blue", size: "large", text, width: "250" } // customization attributes
//     );
//   });

//   return <div ref={googleSignInButton}></div>;
// }
import React, { useEffect } from "react";
import { GoogleLogin } from "@react-oauth/google";
import { GoogleOAuthProvider } from "@react-oauth/google";

const GoogleLogIn = () => {
  return (
    <React.Fragment>
      <GoogleOAuthProvider clientId="wlgns2dnl@gmail.com">
        <GoogleLogin
          buttonText="google login"
          onSuccess={(credentialResponse) => {
            console.log(credentialResponse);
          }}
          onError={() => {
            console.log("Login Failed");
          }}
        />
      </GoogleOAuthProvider>
    </React.Fragment>
  );
};

export default GoogleLogIn;
