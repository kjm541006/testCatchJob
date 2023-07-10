package com.project.catchJob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.domain.Member;
import com.project.catchJob.dto.MemberDTO;
import com.project.catchJob.dto.ResponseDTO;
import com.project.catchJob.security.PasswordEncoder;
import com.project.catchJob.security.TokenProvider;
import com.project.catchJob.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private PasswordEncoder pwdEncoder = new PasswordEncoder();
	
	@PostMapping("/register")
	public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO) {
		try {
			if(memberDTO == null || memberDTO.getPwd() == null) {
				throw new RuntimeException("��й�ȣ ����");
			}
			//if(memberDTO.getEmail().)
			
			// ��û�� �̿��� ������ ��� �����
			Member member = Member.builder()
					.email(memberDTO.getEmail())
					//.pwd(pwdEncoder.encrypt(memberDTO.getPwd())) pwd���ϸ� ���� pwd���� ������ pwd�� ����Ǿ email���� �ٸ��� ����
					.pwd(pwdEncoder.encrypt(memberDTO.getEmail(), memberDTO.getPwd())) 
					.name(memberDTO.getName())
					.job(memberDTO.getJob())
					.hasCareer(memberDTO.getHasCareer())
					.build();
			// ���񽺸� �̿��� �������丮�� ��� �����ϱ�
			Member registerMember = memberService.createMember(member);
			MemberDTO responseMemberDTO = MemberDTO.builder()
					.email(registerMember.getEmail())
					.pwd(registerMember.getName())
					.name(registerMember.getName())
					.job(registerMember.getJob())
					.hasCareer(registerMember.getHasCareer())
					.build();
			return ResponseEntity.ok().body(responseMemberDTO);
		} catch (Exception e) {
			// ��� ������ �׻� �ϳ��̹Ƿ� ����Ʈ�� �������ϴ� ResponseDTO�� ������� �ʰ� �׳� member ����
//			 ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//			 return ResponseEntity.badRequest().body(registerMember(memberDTO));
			 return ResponseEntity.badRequest().body("�ش� �̸����� �̹� �����մϴ�. �ٸ� �̸����� �Է����ּ���.");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
		Member member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPwd(), pwdEncoder);
		
		// log.info("{} �α��� ����", member.toString());
		
		if(member != null) {
			
			// ��ū ����
			final String token = tokenProvider.createToken(member);
			log.info("token ���� ����", token);
			
			final MemberDTO responseMemberDTO = MemberDTO.builder()
					.name(member.getName())
					.email(member.getEmail())
					.pwd(member.getPwd())
					.job(member.getJob())
					.hasCareer(member.getHasCareer())
					.token(token)
					.build();
			
			return ResponseEntity.ok().body(responseMemberDTO);
		}
		else {
			return ResponseEntity.badRequest().body("�α��� ����");
		}
	}
	
	@PostMapping("/memberInfo")
	public ResponseEntity<?> memberInfo(@RequestBody MemberDTO memberDTO) {
		Member member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPwd(), pwdEncoder);
		
		if(member != null) {
			// ���� ȸ�� �������� ���� ������ �÷��� 
			member.setJob(memberDTO.getJob());
			member.setHasCareer(memberDTO.getHasCareer());
			
			Member updateMember = memberService.updateMember(member);
			
			MemberDTO responseMemberDTO = memberDTO.builder()
					.name(updateMember.getName())
					.email(updateMember.getEmail())
					.pwd(updateMember.getPwd())
					.job(updateMember.getJob())
					.hasCareer(updateMember.getHasCareer())
					.token(memberDTO.getToken()) // ��ū�� ������ �߱޹��� ��ū ���
					.build();
			
			return ResponseEntity.ok().body(responseMemberDTO);
		} else {
			return ResponseEntity.badRequest().body("ȸ�� ���� ����");
		}
		
	}
}
