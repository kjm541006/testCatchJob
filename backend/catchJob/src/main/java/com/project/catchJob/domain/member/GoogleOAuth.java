package com.project.catchJob.domain.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleOAuth {

	// https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko 참고
	
	private final String GOOGLE_LOGIN_URL = "https://accounts.google.com";
			
	private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
	
	private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	
	private final ObjectMapper objectMapper;
	
	private RestTemplate restTemplate;
	
	@Value("${oauth2.google.client-id}")
	private String GOOGLE_CLIENT_ID;
	
	@Value("${oauth2.google.client-secret}")
	private String GOOGLE_CLIENT_SECRET;
	
	@Value("${oauth2.google.redirect-uri}")
	private String LOGIN_REDIRECT_URL;

	public String getOauthRedirectURL() {
	    String redirectUrl = "https://accounts.google.com/o/oauth2/auth";
	    String clientId = GOOGLE_CLIENT_ID;
	    String redirectUri = LOGIN_REDIRECT_URL;
	    String responseType = "code";
	    String scope = "openid email profile";
	    String state = "your-state-value"; // 선택 사항: CSRF 보호를 위한 상태 값 추가
	    
	    String oauthUrl = String.format("%s?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s",
	            redirectUrl, clientId, redirectUri, responseType, scope, state);
	    
	    return oauthUrl;
	}

	

	
	
}
