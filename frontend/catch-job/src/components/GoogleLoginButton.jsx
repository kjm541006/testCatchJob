import { useGoogleLogin } from '@react-oauth/google'
import Style from '../assets/css/GoogleLoginButton.module.css'
import axios from 'axios';

const GoogleLoginButton = () => {
  const googleSocialLogin = useGoogleLogin({
    onSuccess: (codeResponse) => {
      axios.post('http://43.202.98.45:8089/googlelogin', { code: codeResponse.code }) 
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    flow: 'auth-code',
  });

  return (
    <div className={`${Style.google_login_box}`} onClick={() => googleSocialLogin()}>
    </div>
  )
}

export default GoogleLoginButton
