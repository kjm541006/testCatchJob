package com.project.catchJob.dto.board;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

	// private Long boardId;
	private String bTitle;
	private String bContents;
	private int bCnt;
	private int bLike;
	private String bFileName;
	private MultipartFile bUploadFile;
	private String bCoverFileName;
	private MultipartFile bCoverUploadFile;
}
