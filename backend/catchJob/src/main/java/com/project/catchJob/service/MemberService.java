package com.project.catchJob.service;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.member.M_profile;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.dto.member.MemberInfoDTO;
import com.project.catchJob.exception.UnauthorizedException;
import com.project.catchJob.repository.member.M_ProfileRepository;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.security.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional // memberRepo와 mProfileRepo 하나라도 실패하면 롤백
public class MemberService {
	
	@Autowired 
	private MemberRepository memberRepo;
	
	@Autowired
	private M_ProfileRepository mProfileRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private CommonService commonService;
	
	@Value("${file.path}") private String filePath;
	
	/*
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
	*/
	
	// 회원가입
	public void createMember(MemberDTO memberDTO) throws Exception {
		// 프로필사진 여부에 따라 로직 분리
		
		final String email = memberDTO.getEmail();
		if(memberRepo.existsByEmail(email)) {
			log.warn("{} 해당 이메일은 이미 존재합니다!", email);
			throw new RuntimeException("이미 존재하는 이메일입니다!");
		}
		if(memberDTO.getMProfile() == null) {
			// 프로필 사진 없음
			Member member = Member.builder()
					.email(memberDTO.getEmail())
					.name(memberDTO.getName())
					.pwd(memberDTO.getPwd())
					.job(memberDTO.getJob())
					.hasCareer(memberDTO.getHasCareer())
					.type(memberDTO.getType())
					.fileAttached(memberDTO.setFileAttached(0))
					.build();
			memberRepo.save(member);
		} else {
			// 프로필 사진 있음
			MultipartFile mProfile = memberDTO.getMProfile(); // DTO에 담긴 파일 가져옴
			String originalFileName = mProfile.getOriginalFilename(); // 파일 이름 가져옴
			String storedFileName = System.currentTimeMillis() + "_" + originalFileName; // 서버 저장용 이름
//			String savePath = "C:/Users/권유진/Desktop/딩딩_학원실습/temp/" + storedFileName; // 저장 경로 설정
			String savePath = filePath + storedFileName; // 저장 경로 설정
			mProfile.transferTo(new File(savePath)); // 해당 경로에 파일 저장
			Member member = Member.builder()
					.email(memberDTO.getEmail())
					.name(memberDTO.getName())
					.pwd(memberDTO.getPwd())
					.job(memberDTO.getJob())
					.hasCareer(memberDTO.getHasCareer())
					.type(memberDTO.getType())
					.fileAttached(memberDTO.setFileAttached(1))
					.build();
			String savedEmail = memberRepo.save(member).getEmail();
			Member saveMember = memberRepo.findByEmail(savedEmail);
			
			M_profile profile = M_profile.toMProfile(saveMember, originalFileName, storedFileName);
			mProfileRepo.save(profile);
			// member와 m_profile에 해당 데이터 저장
		}
	}
	
	
	// 로그인
	public Member getByCredentials(final String email, final String pwd, final PasswordEncoder pwdEncoder) {
		
		final Member originMember = memberRepo.findByEmail(email);
		System.out.println(originMember);
		// matches 메서드를 이용해서 패스워드 같은지 확인
		if(originMember != null && pwdEncoder.matches(pwdEncoder.encrypt(email, pwd), originMember.getPwd())) {
			return originMember;
		}
		return null;
	}
	// 회원조회
	public MemberInfoDTO getInfo(String jwtToken) {
		
		 Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
		    		.orElseThrow(UnauthorizedException::new);
		
		Member findMember = memberRepo.findByEmail(optAuthenticatedMember.getEmail());
		MemberInfoDTO memberInfo = MemberInfoDTO.builder()
				.email(findMember.getEmail())
				.name(findMember.getName())
				.job(findMember.getJob())
				.hasCareer(findMember.getHasCareer())
				.build();
		return memberInfo;
	}
	

	// 회원수정
	public Member updateMember(MemberDTO member) {
		
		Member findMember = memberRepo.findByEmail(member.getEmail());
		PasswordEncoder pwdEncoder = new PasswordEncoder();
		
		if(findMember != null) {
			// 이메일,타입은 수정 불가
			findMember.setName(member.getName());
			findMember.setPwd(pwdEncoder.encrypt(member.getEmail(), member.getPwd()));
			findMember.setJob(member.getJob());
			findMember.setHasCareer(member.getHasCareer());
			findMember.setFileAttached(member.getFileAttached());
			return memberRepo.save(findMember);
		} else {
			throw new RuntimeException("다시 로그인 해주세요");
		}
	}
	
//	public Member findMemberByEmail(String userEmail) throws Exception {
//	    Member member = memberRepo.findByEmail(userEmail);
//	    if (member == null) {
//	        throw new Exception("Member not found with email: " + userEmail);
//	    }
//	    return member;
//	}

//	public Member findMemberByEmail(String userEmail) {
//	    Member member = memberRepo.findByEmail(userEmail);
//	    if (member == null) {
//	        throw new UsernameNotFoundException("Member not found with email: " + userEmail);
//	    }
//	    return member;
//	}
	

	// 회원 정보 조회
	public Member getMember(Long memberId) throws Exception {
        return memberRepo.findById(memberId)
                .orElseThrow(() -> new Exception("해당 회원이 없습니다"));
    }

}
