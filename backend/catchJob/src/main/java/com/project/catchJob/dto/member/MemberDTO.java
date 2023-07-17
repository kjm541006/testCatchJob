package com.project.catchJob.dto.member;


import org.springframework.web.multipart.MultipartFile;
import com.project.catchJob.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@NoArgsConstructor // 기본생성자
public class MemberDTO {
	
	// private Long memberId;
	private String name;
	private String email;
	private String pwd;
	private String job;
	private String hasCareer;
	private String token;
	private String type;
	
	private MultipartFile mProfile;
	private String mOriginalFileName;
	private String mStoredFileName;
	private int fileAttached;
	
	public int setFileAttached(int num) {
		return num;
	}
	
	public static MemberDTO toMemberDTO(Member member) {
		
		MemberDTO memberDTO = new MemberDTO();

		memberDTO.setName(member.getName());
		memberDTO.setEmail(member.getEmail());
//		memberDTO.setPwd(pwdEncoder.encrypt(memberDTO.getEmail(), memberDTO.getPwd()));
		memberDTO.setPwd(member.getPwd());
		memberDTO.setJob(member.getJob());
		memberDTO.setHasCareer(member.getHasCareer());
		memberDTO.setType("일반");
		if(member.getFileAttached() == 0) {
			// 프로필 사진 없는 경우
			memberDTO.setFileAttached(member.getFileAttached());
		} else {
			// 프로필 사진 있는 경우
			memberDTO.setFileAttached(member.getFileAttached());
			// select * from member m, m_profile mp where m.member_id=mp.member_id where m.member_id=?
			memberDTO.setMOriginalFileName(member.getMProfile().getMOriginalFileName());
			memberDTO.setMStoredFileName(member.getMProfile().getMStoredFileName());
		}
		return memberDTO;
	}
	
}
