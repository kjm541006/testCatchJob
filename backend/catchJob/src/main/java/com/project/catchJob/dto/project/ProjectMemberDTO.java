package com.project.catchJob.dto.project;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberDTO {
	
	private Long pMemId;
	private String pMemJob;
	private String pMemReason;
    private String memberName;
	private String memberEmail;	

}
