import { useRef } from 'react';
import useScript from './useScript';

export default function GoogleLogin({
  onGoogleSignIn = () => {},
  text = 'signin_with',
}) {
  const googleSignInButton = useRef(null);
  const client_id =
    '338950793796-fsuh7oog818lj6lvs2ds4suorcgoa61e.apps.googleusercontent.com';

  useScript('https://accounts.google.com/gsi/client', () => {
    window.google.accounts.id.initialize({
      //client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID,
      client_id,
      callback: onGoogleSignIn,
    });
    window.google.accounts.id.renderButton(
      googleSignInButton.current,
      { theme: 'filled_blue', size: 'large', text, width: '250' } // customization attributes
    );
  });

  return <div ref={googleSignInButton}></div>;
}
