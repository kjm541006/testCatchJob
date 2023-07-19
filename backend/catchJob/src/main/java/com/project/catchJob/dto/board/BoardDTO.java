package com.project.catchJob.dto.board;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

	private Long boardId;
	private String bTitle;
	private String bContents;
	private int bCnt;
	private int bLike;
	private String bFileName;
	private MultipartFile bUploadFile;
	private String bCoverFileName;
	private MultipartFile bCoverUploadFile;
	private Date bDate;
	private String mName;
	private String mStoredFileName;

	public static BoardDTO toBoardListDTO(Board board, Member member) {
		
		BoardDTO boardDTO = new BoardDTO();
		
		boardDTO.setBoardId(board.getBoardId());
		boardDTO.setBCnt(board.getBCnt());
		boardDTO.setBLike(board.getBLike());
		boardDTO.setMName(member.getName());
		boardDTO.setMStoredFileName(member.getMProfile().getMStoredFileName());
		boardDTO.setBCoverUploadFile(board.getBCoverUploadFile());
		boardDTO.setBDate((Date) board.getBDate());
		
		return boardDTO;
	}
	
public static BoardDTO toBoardDTO(Board board, Member member) {
		
		BoardDTO boardDTO = new BoardDTO();
		
		boardDTO.setBoardId(board.getBoardId());
		boardDTO.setBCnt(board.getBCnt());
		boardDTO.setBLike(board.getBLike());
		boardDTO.setMName(member.getName());
		boardDTO.setMStoredFileName(member.getMProfile().getMStoredFileName());
		boardDTO.setBCoverUploadFile(board.getBCoverUploadFile());
		boardDTO.setBDate((Date) board.getBDate());
		
		return boardDTO;
	}
	
}

