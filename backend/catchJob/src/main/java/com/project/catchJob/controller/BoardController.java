package com.project.catchJob.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.B_commentsDTO;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.board.CommentResponse;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.board.B_likeRepository;
import com.project.catchJob.security.TokenProvider;
import com.project.catchJob.service.BoardService;
import com.project.catchJob.service.CommonService;
import com.project.catchJob.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class BoardController {
	
	@Value("${file.path}") private String filePath;
	@Autowired MemberService memberService;
	@Autowired BoardService boardService;
	@Autowired B_likeRepository bLikeRepo;
	@Autowired TokenProvider tokenProvider;
	@Autowired CommonService commonService;
	
    @Autowired
    public BoardController(CommonService commonService, BoardService boardService) {
        this.commonService = commonService;
        this.boardService = boardService;
    }

	// 글 목록
	@GetMapping("/")
	public ResponseEntity<List<BoardDTO>> getBoardList(
			@RequestHeader(value="Authorization", 
			required = false) String jwtToken) {
		List<BoardDTO> boardDTOList = boardService.getBoardList(jwtToken);
		return ResponseEntity.ok(boardDTOList);
	}
	
	
	// 글 등록
	@PostMapping("/portfolio/build")
	public ResponseEntity<?> registerBoard(
	        @RequestBody BoardDTO boardDTO, 
	        @RequestHeader("Authorization") String jwtToken, 
	        @RequestPart(value = "file", required = false) MultipartFile file) 
	        throws Exception {
		
	    Member authenticatedMember = commonService.getAuthenticatedMember(jwtToken).orElseThrow(UnauthorizedException::new);

	    MemberDTO memberDTO = MemberDTO.toMemberDTO(authenticatedMember);

	    boardService.create(boardDTO, memberDTO, file);
	    return ResponseEntity.ok().build();
	}
	
	// 댓글 등록
	@PostMapping("/portfolio/{board_id}")
	public ResponseEntity<?> registerComment(
			@RequestBody B_commentsDTO commentDTO,
			@PathVariable("board_id") Long boardId,
			@RequestHeader("Authorization") String jwtToken) {
		
		Member authenticatedMember = commonService.getAuthenticatedMember(jwtToken).orElseThrow(UnauthorizedException::new);
		
		MemberDTO memberDTO = MemberDTO.toMemberDTO(authenticatedMember);
		
		CommentResponse commentRes = boardService.createComment(commentDTO, memberDTO, boardId);
		
		return ResponseEntity.ok(commentRes);
		
	}

	// 글 수정
	@PostMapping("/portfolio/update/{board_id}")
	public ResponseEntity<?> editBoard(
	        @RequestBody BoardDTO boardDTO, 
	        @RequestHeader("Authorization") String jwtToken, 
	        @RequestPart(value = "file", required = false) MultipartFile file) 
	        throws Exception {
		
	    Member authenticatedMember = commonService.getAuthenticatedMember(jwtToken).orElseThrow(UnauthorizedException::new);

	    MemberDTO memberDTO = MemberDTO.toMemberDTO(authenticatedMember);

	    boardService.update(boardDTO, memberDTO, file);
	    return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/{board_id}/like")
	public ResponseEntity<?> like(@RequestParam Long memberId, @RequestParam Long boardId) throws Exception {
		boolean like = boardService.findLike(memberId, boardId);
		if(like == true) {
			boardService.insert(memberId, boardId);
			return ResponseEntity.ok("하트 ++");
		} else {
			boardService.delete(memberId, boardId);
			return ResponseEntity.ok("하트 --");
		}
	}

	
}
