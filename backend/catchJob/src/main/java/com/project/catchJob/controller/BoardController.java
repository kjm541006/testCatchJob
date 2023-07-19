package com.project.catchJob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.service.BoardService;
import com.project.catchJob.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class BoardController {
	
	@Autowired BoardService boardService;
	/*
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody B_likeDTO bLikeDTO) throws Exception {
		boardService.insert(bLikeDTO);
		return ResponseEntity.ok("하트 ++");
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody B_likeDTO bLikeDTO) throws Exception {
		boardService.delete(bLikeDTO);
		return ResponseEntity.ok("하트 --");
	}
	*/
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestParam Long memberId, @RequestParam Long boardId) throws Exception {
	    boardService.insert(memberId, boardId);
	    return ResponseEntity.ok("하트 ++");
	}

	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestParam Long memberId, @RequestParam Long boardId) throws Exception {
	    boardService.delete(memberId, boardId);
	    return ResponseEntity.ok("하트 --");
	}


	
	@GetMapping("/")
	public BoardDTO getBoardList(@PathVariable Long member_id) throws Exception {
		Board board = boardService.getBoard(member_id);
		Member member = MemberService.getMember(board.getMember().getMemberId());
		return BoardDTO.toBoardListDTO(board, member);
	}
	
	// 글등록
	@PostMapping("/portfolio/register")
	public ResponseEntity<?> registerBoard(@RequestBody BoardDTO boardDTO, @RequestBody MemberDTO memberDTO) throws Exception {
		if(boardDTO.getBoardId() == null) {
			throw new Exception("게시글 없음");
		}
		BoardDTO responseBoardDTO = BoardDTO.builder()
				.bTitle(boardDTO.getBTitle())
				.bContents(boardDTO.getBContents())
				.bFileName(boardDTO.getBFileName())
				.bUploadFile(boardDTO.getBUploadFile())
				.bCoverUploadFile(boardDTO.getBCoverUploadFile())
				.bCoverFileName(boardDTO.getBCoverFileName())
				.mName(memberDTO.getEmail())
				.build();
		boardService.create(responseBoardDTO, memberDTO);
		return ResponseEntity.ok().body(responseBoardDTO);
	}

	@GetMapping("/{member_id}")
	public BoardDTO getBoard(@PathVariable Long member_id) throws Exception {
		Board board = boardService.getBoard(member_id);
		Member member = MemberService.getMember(board.getMember().getMemberId());
		return BoardDTO.toBoardDTO(board, member);
	}

	
}
