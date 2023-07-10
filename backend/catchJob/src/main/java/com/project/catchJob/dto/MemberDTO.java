package com.project.catchJob.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	//private Long memberId;
	private String name;
	private String email;
	private String pwd;
	private List<String> job;
	private String hasCareer;
	private String token;
}
