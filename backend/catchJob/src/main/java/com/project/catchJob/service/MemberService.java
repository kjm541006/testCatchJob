package com.project.catchJob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.MemberRepository;
import com.project.catchJob.security.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	// 회원가입
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
	
	// 로그인
	public Member getByCredentials(final String email, final String pwd, final PasswordEncoder pwdEncoder) {
		
		final Member originMember = memberRepo.findByEmail(email);
		
		// matches 메서드를 이용해서 패스워드 같은지 확인
		if(originMember != null && pwdEncoder.matches(pwdEncoder.encrypt(email, pwd), originMember.getPwd())) {
			return originMember;
		}
		return null;
	}
	
	// 회원수정
	public Member updateMember(MemberDTO member) {
		
		Member findMember = memberRepo.findByEmail(member.getEmail());
		PasswordEncoder pwdEncoder = new PasswordEncoder();
		
		if(findMember != null) {
			// 이메일은 수정 불가
			findMember.setName(member.getName());
			findMember.setPwd(pwdEncoder.encrypt(member.getEmail(), member.getPwd()));
			findMember.setJob(member.getJob());
			findMember.setHasCareer(member.getHasCareer());
			
			return memberRepo.save(findMember);
		} else {
			throw new RuntimeException("다시 로그인 해주세요");
		}
	}
//	public Member updateMember(Member member) {
//		
//		Member findMember = memberRepo.findByEmail(member.getEmail());
//		PasswordEncoder pwdEncoder = new PasswordEncoder();
//		
//		if(findMember != null) {
//			// 이메일은 수정 불가
//			findMember.setName(member.getName());
//			findMember.setPwd(pwdEncoder.encrypt(member.getEmail(), member.getPwd()));
//			findMember.setJob(member.getJob());
//			findMember.setHasCareer(member.getHasCareer());
//			
//			return memberRepo.save(findMember);
//		} else {
//			throw new RuntimeException("다시 로그인 해주세요");
//		}
//	}
}
