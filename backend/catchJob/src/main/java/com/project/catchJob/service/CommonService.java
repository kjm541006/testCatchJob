package com.project.catchJob.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.security.TokenProvider;

@Service
public class CommonService {
    private final MemberRepository memberRepo;
    private final TokenProvider tokenProvider;

    @Autowired
    public CommonService(MemberRepository memberRepository, TokenProvider tokenProvider) {
        this.memberRepo = memberRepository;
        this.tokenProvider = tokenProvider;
    }
    
	// 인증 관련 메소드 구현
	public Optional<Member> getAuthenticatedMember(String jwtToken) {
	    if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
	        return Optional.empty();
	    }

	    String token = jwtToken.substring(7);
	    boolean isValidToken = tokenProvider.validateToken(token);

	    if (!isValidToken) {
	        return Optional.empty();
	    }

	    String userEmail = tokenProvider.getUserEmail(token);
	    return memberRepo.findOptionalByEmail(userEmail); // 새로 추가한 메소드 호출
	}
}
