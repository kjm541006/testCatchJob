package com.project.catchJob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.catchJob.domain.member.GoogleOAuth;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.member.GoogleOAuthTokenDTO;
import com.project.catchJob.dto.member.GoogleUserInfoDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.security.PasswordEncoder;
import com.project.catchJob.security.TokenProvider;

@Service
public class OAuthService {
	
	@Autowired
	private GoogleOAuth googleOauth;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private GoogleUserInfoDTO getGoogleUserInfoDTO(String code) throws JsonProcessingException {
		
		ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
		GoogleOAuthTokenDTO oAuthToken = googleOauth.getAccessToken(accessTokenResponse);
		ResponseEntity<String> userInfoRes = googleOauth.requestUserInfo(oAuthToken);
		GoogleUserInfoDTO googleUser = googleOauth.getUserInfo(userInfoRes);
		return googleUser;
	}

	public MemberDTO createGoogleUser(String code) throws Exception {
		GoogleUserInfoDTO googleUser = getGoogleUserInfoDTO(code);
		
		if(!memberRepo.existsByEmail(googleUser.getEmail())) {
			Member newMember = Member.builder()
						.email(googleUser.getEmail())
						.name(googleUser.getName())
						.pwd("") // 비밀번호는 공란으로 기입
						.job("기타")
						.hasCareer("신입")
						.type("구글")
						.build();
			memberRepo.save(newMember);
			
			MemberDTO memberDTO = MemberDTO.fromMember(newMember);
			
			// 토큰 생성
			String token = tokenProvider.createToken(newMember);
			memberDTO.setToken(token);
			return memberDTO;
		} 
		return null;
	}
	
}
