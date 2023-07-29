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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.catchJob.dto.member.GoogleOAuthTokenDTO;
import com.project.catchJob.dto.member.GoogleUserInfoDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
	public Mono<GoogleOAuthTokenDTO> requestAccessToken(String accessCode) {
	    WebClient webClient = WebClient.create();

	    return webClient.post()
	        .uri(GOOGLE_TOKEN_URL)
	        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	        .body(BodyInserters
	            .fromFormData("code", accessCode)
	            .with("client_id", googleClientId)
	            .with("client_secret", googleClientSecret)
	            .with("redirect_uri", googleRedirectUrl)
	            .with("grant_type", "authorization_code"))
	        .retrieve()
	        .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RuntimeException("Error: " + clientResponse.statusCode())))
	        .bodyToMono(GoogleOAuthTokenDTO.class);
	}

	public Mono<ResponseEntity<String>> requestUserInfo(GoogleOAuthTokenDTO oAuthToken) {
	    WebClient webClient = WebClient.create();
	        
	    return webClient.get()
	        .uri(GOOGLE_USERINFO_REQUEST_URL)
	        .header("Authorization", "Bearer " + oAuthToken.getAccess_token())
	        .retrieve()
	        .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RuntimeException("Error: " + clientResponse.statusCode())))
	        .toEntity(String.class);
	}

	public Mono<GoogleUserInfoDTO> getUserInfo(ResponseEntity<String> res) {
	    return Mono.just(res.getBody()).map(body -> {
	        try {
	            return objectMapper.readValue(body, GoogleUserInfoDTO.class);
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Error: Cannot convert JSON to GoogleUserInfoDTO", e);
	        }
	    });
	}

	

	
}