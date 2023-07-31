package com.project.catchJob.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDTO {
	
	private String email;
	private String name;
	private String job;
	private String hasCareer;

}
