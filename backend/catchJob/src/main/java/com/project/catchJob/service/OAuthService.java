package com.project.catchJob.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.project.catchJob.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OAuthService {

	// https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko 참고
	/*
	private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
	// 엑세스 토큰에 대한 승인 코드를 교환하려면 엔드포인트 호출 후 매개변수 설정
	
	@Value("${oauth2.google.client-id}")
	private String GOOGLE_CLIENT_ID;
	
	@Value("${oauth2.google.client-secret}")
	private String GOOGLE_CLIENT_SECRET;
	
	@Value("${oauth2.google.redirect-uri}")
	private String LOGIN_REDIRECT_URL;
	
	@Autowired
	private final MemberRepository memberRepo;
	
	public ResponseEntity<?> getGoogleAccessToken(String accessCode) {
		
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		
		params.put("code", accessCode);
		params.put("client-id", GOOGLE_CLIENT_ID);
		params.put("client-secret", GOOGLE_CLIENT_SECRET);
		params.put("redirect-uri", LOGIN_REDIRECT_URL);
		params.put("grant_type", "authorization_code");
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
		// 스프링부트에서 다른 서버의 api 엔드포인트 호출할 때 restTemplate사용
		
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity;
		} 
		return null;
		
	}
	*/
}
