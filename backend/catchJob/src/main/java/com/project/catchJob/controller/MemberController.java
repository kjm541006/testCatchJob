package com.project.catchJob.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.domain.member.GoogleOAuth;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.member.GoogleUserInfoDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.dto.member.MemberInfoDTO;
import com.project.catchJob.security.PasswordEncoder;
import com.project.catchJob.security.TokenProvider;
import com.project.catchJob.service.BoardService;
import com.project.catchJob.service.MemberService;
import com.project.catchJob.service.OAuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private GoogleOAuth googleoauth;
	
	@Autowired
	private OAuthService oAuthService;
	

	
	// 회원등록
	@PostMapping("/register")
	public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO) {
		try {
			if(memberDTO == null || memberDTO.getPwd() == null) {
				throw new RuntimeException("비밀번호 공란");
			}
			MemberDTO responseMemberDTO = MemberDTO.builder()
					.email(memberDTO.getEmail())
					.pwd(pwdEncoder.encrypt(memberDTO.getEmail(), memberDTO.getPwd()))
					.name(memberDTO.getName())
					.job(memberDTO.getJob())
					.hasCareer(memberDTO.getHasCareer())
					.type("일반")
					.build();
			memberService.createMember(responseMemberDTO);
			return ResponseEntity.ok().body(responseMemberDTO);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("해당 이메일은 이미 존재합니다. 다른 이메일을 입력해주세요.");
		}		
	}
	
	
	/*

	// 회원등록
	@PostMapping("/register") 
	public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO) {
		try {
			if(memberDTO == null || memberDTO.getPwd() == null) {
				throw new RuntimeException("비밀번호 공란");
			}
			
			// 요청을 이용해 저장할 멤버 만들기
			Member member = Member.builder()
					.email(memberDTO.getEmail())
					//.pwd(pwdEncoder.encrypt(memberDTO.getPwd())) pwd로하면 같은 pwd끼리 동일한 pwd로 저장되어서 email별로 다르게 저장
					.pwd(pwdEncoder.encrypt(memberDTO.getEmail(), memberDTO.getPwd())) 
					.name(memberDTO.getName())
					.job(memberDTO.getJob())
					.hasCareer(memberDTO.getHasCareer())
					.build();
			// 서비스를 이용해 리포지토리에 멤버 저장하기
			Member registerMember = memberService.createMember(member);
			MemberDTO responseMemberDTO = MemberDTO.builder()
					.email(registerMember.getEmail())
					.pwd(registerMember.getPwd())
					.name(registerMember.getName())
					.job(registerMember.getJob())
					.hasCareer(registerMember.getHasCareer())
					.build();
			return ResponseEntity.ok().body(responseMemberDTO);
		} catch (Exception e) {
			// 멤버 정보는 항상 하나이므로 리스트로 만들어야하는 ResponseDTO를 사용하지 않고 그냥 member 리턴
//			 ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//			 return ResponseEntity.badRequest().body(registerMember(memberDTO));
			 return ResponseEntity.badRequest().body("해당 이메일은 이미 존재합니다. 다른 이메일을 입력해주세요.");
		}
	}
	*/
	// 로그인
	@PostMapping("/login") 
	public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
		Member member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPwd(), pwdEncoder);
		
		// log.info("{} 로그인 성공", member.toString());
		if(member != null) {
			
			// 토큰 생성
			final String token = tokenProvider.createToken(member);
			log.info("token 생성 성공", token);
			
			final MemberDTO responseMemberDTO = MemberDTO.builder()
					.name(member.getName())
					.email(member.getEmail())
					.pwd(pwdEncoder.encrypt(member.getEmail(), member.getPwd()))
					.job(member.getJob())
					.hasCareer(member.getHasCareer())
					.token(token)
					.build();
			
			return ResponseEntity.ok().body(responseMemberDTO);
		}
		else {
			return ResponseEntity.badRequest().body("로그인 실패");
		}
	}
	
//	// 구글 로그인
//	@PostMapping("/login/oauth2/code/google")
	@PostMapping("/googlelogin")
	public ResponseEntity<?> successGoogleLogin(@RequestParam("code") String code) {
		return googleoauth.requestAccessToken(code);
	}

	// 마이페이지 (회원조회)
	@GetMapping("/memberInfo")
	public ResponseEntity<?> memberInfo(@RequestHeader("Authorization") String jwtToken) {
		
		MemberInfoDTO memberInfo = memberService.getInfo(jwtToken);
		if(memberInfo != null) {
			return ResponseEntity.ok().body(memberInfo);
		}
		return ResponseEntity.badRequest().body("회원 조회 실패");
	}
//	public ResponseEntity<?> memberInfo(@RequestBody MemberDTO memberDTO) {
//		Member member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPwd(), pwdEncoder);
//		
//		if(member != null) {
//			MemberDTO responseMemberDTO = MemberDTO.toMemberDTO(member);
//			return ResponseEntity.ok().body(responseMemberDTO);
//		}
//		return ResponseEntity.badRequest().body("회원 조회 실패");
//	}
	
	// 마이페이지 (비번조회)
	@PostMapping("/memberPwd")
	public ResponseEntity<?> checkPwd(@RequestHeader("Authorization") String jwtToken, @RequestBody MemberDTO memberDTO) {
		
		String res = memberService.checkPwd(jwtToken, memberDTO, pwdEncoder);
		if(res != null) {
			return ResponseEntity.ok().body("비밀번호 일치");
		} 
		return ResponseEntity.badRequest().body("비밀번호 불일치");
	}
	
	// 마이페이지 (회원수정)
	@PutMapping("/memberUpdate")
	public ResponseEntity<?> memberUpdate(@RequestHeader("Authorization") String jwtToken, @RequestBody MemberDTO memberDTO) {
		Member updateMember = memberService.updateMember(jwtToken, memberDTO);
		
		if(updateMember != null) {
			return ResponseEntity.ok().body(updateMember);
		} 
		return ResponseEntity.badRequest().body("회원 수정 실패");

	}
//	public ResponseEntity<?> memberUpdate(@RequestBody MemberDTO memberDTO) {
//		Member updateMember = memberService.updateMember(memberDTO);
//		
//		if(updateMember != null) {
//			MemberDTO responseMemberDTO = memberDTO.builder()
//					.email(memberDTO.getEmail()) // 이메일은 수정불가. 기존의 이메일
//					.name(updateMember.getName())
//					.pwd(updateMember.getPwd())
//					.job(updateMember.getJob())
//					.hasCareer(updateMember.getHasCareer())
//					.token(memberDTO.getToken()) // 토큰은 기존의 발급받은 토큰 사용
//					.type(memberDTO.getType())
//					.fileAttached(updateMember.getFileAttached())
//					.build();
//			
//			return ResponseEntity.ok().body(responseMemberDTO);
//		} else {
//			return ResponseEntity.badRequest().body("회원 수정 실패");
//		}
//	}

}