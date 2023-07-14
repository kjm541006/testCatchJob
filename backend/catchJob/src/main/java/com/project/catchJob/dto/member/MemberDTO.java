package com.project.catchJob.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	// private Long memberId;
	private String name;
	private String email;
	private String pwd;
	private String job;
	private String hasCareer;
	private String token;
}
