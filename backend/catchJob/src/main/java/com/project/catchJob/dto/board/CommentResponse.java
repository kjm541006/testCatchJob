package com.project.catchJob.dto.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentResponse {

	private LocalDateTime commentDate;
	
	public CommentResponse(LocalDateTime commentDate) {
		this.commentDate = commentDate;
	}
	

}
