package com.project.catchJob.dto.board;

import java.time.LocalDateTime;

public class CommentResponse {

	private LocalDateTime commentDate;
	
	public CommentResponse(LocalDateTime commentDate) {
		this.commentDate = commentDate;
	}
	
	public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setComDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }
}
