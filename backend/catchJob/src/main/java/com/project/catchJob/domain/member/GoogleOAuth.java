package com.project.catchJob.domain.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.catchJob.dto.member.GoogleOAuthTokenDTO;
import com.project.catchJob.dto.member.GoogleUserInfoDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.properties")
public class GoogleOAuth {

	// https://velog.io/@hwsa1004/Spring-%EA%B5%AC%EA%B8%80-%EB%A1%9C%EA%B7%B8%EC%9D%B8-REST-API-%EA%B5%AC%ED%98%84-OAuth2
	// https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko 참고
	
	private final String GOOGLE_LOGIN_URL = "https://accounts.google.com";
			
	private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
	
	private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	
	private final ObjectMapper objectMapper;
	
	private RestTemplate restTemplate;
	
//	@Value("${spring.security.oauth2.client.registration.google.clientId}")
//	private String googleClientId;
//	
//	@Value("${spring.security.oauth2.client.registration.google.clientSecret}")
//	private String googleClientSecret;
//	
//	@Value("${spring.security.oauth2.client.registration.google.redirect}")
//	private String googleRedirectUrl;


//	public String getOauthRedirectURL() {
//
//		String redirectUrl = "https://accounts.google.com/o/oauth2/auth";
////	    String clientId = googleClientId;
////	    String redirectUri = googleRedirectUrl;
//	    String responseType = "code";
//	    String scope = "openid email profile";
//	    String state = "your-state-value"; // 선택 사항: CSRF 보호를 위한 상태 값 추가
//	    
//	    String oauthUrl = redirectUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri +
//	            "&response_type=" + responseType + "&scope=" + scope + "&state=" + state;
//	    
//	    return oauthUrl;
//	}

	// 일회용 코드를 다시 구글로 보내 엑세스 토큰을 포함한 json string이 담긴 responseEntity 받아옴
	public ResponseEntity<String> requestAccessToken(String accessCode) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		
		params.put("code", accessCode);
		params.put("client-id", "226990065119-dh4qnntmuprddppr3hoi6umt9k99vkvb.apps.googleusercontent.com");
//		params.put("client-secret", googleClientSecret);
//		params.put("redirect-uri", googleRedirectUrl);
		params.put("grant_type", "authorization_code");
		
		 RestTemplate restTemplate1 = new RestTemplate();

		    // Add a custom error handler
		    restTemplate1.setErrorHandler(new ResponseErrorHandler() {
		        @Override
		        public boolean hasError(ClientHttpResponse response) throws IOException {
		            return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
		                    || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
		        }

		        @Override
		        public void handleError(ClientHttpResponse response) throws IOException {
		            String responseBody = new BufferedReader(new InputStreamReader(response.getBody()))
		                    .lines().collect(Collectors.joining("\n"));

		            System.out.println("ClientHttpResponse Status Code: " + response.getStatusCode());
		            System.out.println("ClientHttpResponse Status Text: " + response.getStatusText());
		            System.out.println("ClientHttpResponse Response Body: " + responseBody);
		        }
		    });

		
		
		
		ResponseEntity<String> responseEntity = restTemplate1.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
		// 스프링부트에서 다른 서버의 api 엔드포인트 호출할 때 restTemplate사용
		System.out.println("-------res-------" + responseEntity.getBody());
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			System.out.println("=======ok========" + responseEntity.getBody());
			return responseEntity;
		} 
		System.out.println("=======null========" + responseEntity.getBody());
		return ResponseEntity.badRequest().body("fail");
	}
	
	// token 얻기 json -> 자바 객체
	public GoogleOAuthTokenDTO getAccessToken(ResponseEntity<String> res) throws JsonProcessingException {
		System.out.println("response.getBody() = " + res.getBody());
		GoogleOAuthTokenDTO googleOAuthTokenDTO = objectMapper.readValue(res.getBody(), GoogleOAuthTokenDTO.class);
		return googleOAuthTokenDTO;
		// GoogleOAuthTokenDTO : json형태를 자바 객체 형식으로 변경 후 저장해서 담을 곳
	}

	// get으로 token받아와서 사용자 정보 요청
	public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDTO oAuthToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer" + oAuthToken.getAccess_token());
		
		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(headers);
		ResponseEntity<String> res = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, req, String.class);
		System.out.println("response.getBody() = " + res.getBody());
		return res;
	}
	
	// 요청해서 받은 사용자 정보 json -> 자바 객체
	public GoogleUserInfoDTO getUserInfo(ResponseEntity<String> res) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		GoogleUserInfoDTO googleUserInfoDTO = objectMapper.readValue(res.getBody(), GoogleUserInfoDTO.class);
		return googleUserInfoDTO;
		// GoogleUserInfoDTO : json형태를 자바 객체 형식으로 변경 후 저장해서 담을 곳
	}
	
}