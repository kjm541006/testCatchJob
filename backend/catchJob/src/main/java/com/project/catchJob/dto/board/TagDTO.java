package com.project.catchJob.dto.board;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class TagDTO {

	private Long tagId;
	private String tagName;

}
