package com.project.catchJob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.catchJob.domain.member.GoogleOAuth;
import com.project.catchJob.dto.member.GoogleOAuthTokenDTO;
import com.project.catchJob.dto.member.GoogleUserInfoDTO;

import reactor.core.publisher.Mono;

@Service
public class OAuthService {
	
	@Autowired
	private GoogleOAuth googleOauth;
	
	public Mono<GoogleUserInfoDTO> getGoogleUserInfoDTO(String code) {
        
	    return googleOauth.requestAccessToken(code)
	        .flatMap(oAuthToken -> googleOauth.requestUserInfo(oAuthToken))
	        .flatMap(userInfoRes -> googleOauth.getUserInfo(userInfoRes));
	}

	
}