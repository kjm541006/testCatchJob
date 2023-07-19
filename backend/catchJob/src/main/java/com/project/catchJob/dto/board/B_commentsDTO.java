package com.project.catchJob.dto.board;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class B_commentsDTO {

	private Long commentId;
    private String commentContent;
    private Date commentDate;
    private String memberName;
	private String memberEmail;
}
