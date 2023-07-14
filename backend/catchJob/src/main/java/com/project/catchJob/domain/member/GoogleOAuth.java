package com.project.catchJob.domain.member;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.properties")
public class GoogleOAuth {

	// https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko 참고
	
	private final String GOOGLE_LOGIN_URL = "https://accounts.google.com";
			
	private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
	
	private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	
	private final ObjectMapper objectMapper;
	
	private RestTemplate restTemplate;
	
	@Value("${google.clientId}")
	//private String GOOGLE_CLIENT_ID;
	private String googleClientId;
	
	@Value("${google.clientSecret}")
	//private String GOOGLE_CLIENT_SECRET;
	private String googleClientSecret;
	
	@Value("${google.redirect}")
	//private String LOGIN_REDIRECT_URL;
	private String googleRedirectUrl;

	@PostConstruct
	public String getOauthRedirectURL() {

		String redirectUrl = "https://accounts.google.com/o/oauth2/auth";
	    String clientId = googleClientId;
	    String redirectUri = googleRedirectUrl;
	    String responseType = "code";
	    String scope = "openid email profile";
	    String state = "your-state-value"; // 선택 사항: CSRF 보호를 위한 상태 값 추가
	    
	    String oauthUrl = redirectUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri +
	            "&response_type=" + responseType + "&scope=" + scope + "&state=" + state;
	    
	    return oauthUrl;
	    


	}
	
}
