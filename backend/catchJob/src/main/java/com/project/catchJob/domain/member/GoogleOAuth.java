package com.project.catchJob.domain.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
	
	@Value("${spring.security.oauth2.client.registration.google.clientId}")
	private String googleClientId;
	
	@Value("${spring.security.oauth2.client.registration.google.clientSecret}")
	private String googleClientSecret;
	
	@Value("${spring.security.oauth2.client.registration.google.redirect}")
	private String googleRedirectUrl;


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

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
	    bodyParams.add("code", accessCode);
	    bodyParams.add("client_id", googleClientId);
	    bodyParams.add("client_secret", googleClientSecret);
	    bodyParams.add("redirect_uri", googleRedirectUrl);
	    bodyParams.add("grant_type", "authorization_code");

	    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

	    try {
	        ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_TOKEN_URL, requestEntity, String.class);
	        return response;
	    } catch (HttpClientErrorException e) {
	        System.out.println("Error response body: " + e.getResponseBodyAsString());
	        e.printStackTrace();
	        return new ResponseEntity<>("Error: " + e.getStatusCode() + " " + e.getStatusText(), HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Error: Unknown issue", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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