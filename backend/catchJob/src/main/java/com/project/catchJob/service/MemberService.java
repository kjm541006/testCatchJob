package com.project.catchJob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.Member;
import com.project.catchJob.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	public Member createMember(Member member) {
		if(member == null || member.getEmail() == null) {
			throw new RuntimeException("공란 입력하였습니다!");
		}
		final String email =  member.getEmail();
		if(memberRepo.existsByEmail(email)) {
			log.warn("{} 해당 이메일은 이미 존재합니다!", email);
			throw new RuntimeException("이미 존재하는 이메일입니다!");
		}
		return memberRepo.save(member);
	}
	

}
