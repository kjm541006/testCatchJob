import { useGoogleLogin } from '@react-oauth/google'
import Style from '../assets/css/GoogleLoginButton.module.css'

const GoogleLoginButton = () => {
  const googleSocialLogin = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    flow: 'auth-code',
  })

  return (
    <div className={`${Style.google_login_box}`} onClick={() => googleSocialLogin()}>
    </div>
  )
}

export default GoogleLoginButton
