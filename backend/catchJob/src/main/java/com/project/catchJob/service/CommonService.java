package com.project.catchJob.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.repository.board.BoardRepository;
import com.project.catchJob.repository.community.CommunityPostRepository;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.repository.project.ProjectRepository;
import com.project.catchJob.security.TokenProvider;

@Service
public class CommonService {
	private final TokenProvider tokenProvider;
    private final MemberRepository memberRepo;
    private BoardRepository boardRepo;
    private ProjectRepository projectRepo;
    private CommunityPostRepository commRepo;

    @Autowired
    public CommonService(TokenProvider tokenProvider, 
    					MemberRepository memberRepo,
    					BoardRepository boardRepo,
    					ProjectRepository projectRepo,
    					CommunityPostRepository commRepo) {
    	this.tokenProvider = tokenProvider;
        this.memberRepo = memberRepo;
        this.boardRepo = boardRepo;
        this.projectRepo = projectRepo;
        this.commRepo = commRepo;
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
	
	// 검색
	public List<Object> search(String keyword) {
		List<Object> results = new ArrayList<>();
		
		// 제목
		results.addAll(boardRepo.findByBTitleContaining(keyword));
		results.addAll(projectRepo.findByTitleContaining(keyword));
		results.addAll(commRepo.findByTitleContaining(keyword));
		
		// 사용자가 작성한 글
		results.addAll(boardRepo.findByMemberNameContaining(keyword));
		results.addAll(projectRepo.findByMemberNameContaining(keyword));
//		results.addAll(commRepo.findByMemberContaining(keyword));
		
		// 태그
		results.addAll(boardRepo.findByTagsContaining(keyword));
		
		return results;
		
	}
}
//private final MemberRepository memberRepo;
//private final TokenProvider tokenProvider;
//
//@Autowired
//public CommonService(MemberRepository memberRepository, TokenProvider tokenProvider) {
//	this.memberRepo = memberRepository;
//	this.tokenProvider = tokenProvider;
//}
//
//// 인증 관련 메소드 구현
//public Optional<Member> getAuthenticatedMember(String jwtToken) {
//	if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
//		return Optional.empty();
//	}
//	String token = jwtToken.substring(7);
//	boolean isValidToken = tokenProvider.validateToken(token);
//	
//	if (!isValidToken) {
//		return Optional.empty();
//	}
//	
//	String userEmail = tokenProvider.getUserEmail(token);
//	return memberRepo.findOptionalByEmail(userEmail); // 새로 추가한 메소드 호출
//}
//}
