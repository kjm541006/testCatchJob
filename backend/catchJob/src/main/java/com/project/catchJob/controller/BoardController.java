package com.project.catchJob.controller;

import java.util.List;

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
import com.project.catchJob.repository.board.B_likeRepository;
import com.project.catchJob.service.BoardService;
import com.project.catchJob.service.MemberService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class BoardController {
	
	@Autowired BoardService boardService;
	@Autowired B_likeRepository bLikeRepo;


	// 글 목록
	@GetMapping("/")
	public ResponseEntity<List<BoardDTO>> getBoardList(@RequestBody Member member) {
		List<BoardDTO> boardDTOList = boardService.getBoardList(member);
		return ResponseEntity.ok(boardDTOList);
	}
	
	// 글 등록
	@PostMapping("/portfolio/register")
	public ResponseEntity<?> registerBoard(@RequestBody BoardDTO boardDTO, @RequestBody MemberDTO memberDTO) throws Exception {
		if(boardDTO.getBoardId() != null) {
			throw new Exception("게시글이 이미 존재합니다!");
		}
		BoardDTO responseBoardDTO = BoardDTO.builder()
				.bTitle(boardDTO.getBTitle())
				.bContents(boardDTO.getBContents())
				.bFileName(boardDTO.getBFileName())
				.bCoverFileName(boardDTO.getBCoverFileName())
				.member(memberDTO)
				.tags(boardDTO.getTags())
				.build();
		boardService.create(responseBoardDTO, memberDTO);
		return ResponseEntity.ok().body(responseBoardDTO);
	}

	// 글 수정
	@PostMapping("/")
	
	// 글 조회
	@GetMapping("/{board_id}")
	public ResponseEntity<BoardDTO> getBoard(@PathVariable Long board_id) {
		try {
			Board board = boardService.getBoard(board_id);
			Member member = MemberService.getMember(board.getMember().getMemberId());
			BoardDTO boardDTO =  BoardDTO.toDTO(board, member, bLikeRepo);
			return ResponseEntity.ok(boardDTO);
		} catch (Exception e) {
			// 게시글이 없는 경우 404에러
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	
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
	
}
