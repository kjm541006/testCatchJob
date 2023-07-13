import { useGoogleLogin } from '@react-oauth/google'
import Style from '../assets/css/GoogleLoginButton_long.module.css'

const GoogleLoginButton_long = () => {
  const googleSocialLogin = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    flow: 'auth-code',
  })

  return (
    <div className={`${Style.button}`} onClick={() => googleSocialLogin()}>
       <div className={`${Style.buttonIconGoogle}`}></div>
       <div className={`${Style.buttonText}`}>구글 계정으로 가입하기</div>
    </div>
  )
}

export default GoogleLoginButton_long
