package com.project.catchJob.dto.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class B_commentsDTO {

	//private Long commentId;
	
    private String commentContent;
    
    private LocalDateTime commentDate;
    private String memberName;
	private String memberEmail;
}
