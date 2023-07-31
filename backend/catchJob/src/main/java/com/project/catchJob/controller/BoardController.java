package com.project.catchJob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.B_commentsDTO;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.board.CommentResponse;
import com.project.catchJob.exception.UnauthorizedException;
import com.project.catchJob.repository.board.B_commentsRepository;
import com.project.catchJob.repository.board.B_likeRepository;
import com.project.catchJob.repository.board.BoardRepository;
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
	@Autowired BoardRepository boardRepo;
	@Autowired B_commentsRepository bCommRepo;
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
//	@PostMapping("/portfolio/build")
	@PostMapping(value = " /buildportfolio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> registerBoard(
//	        @RequestBody BoardDTO boardDTO, 
	        @RequestPart(value = "board") BoardDTO boardDTO, 
	        @RequestHeader("Authorization") String jwtToken, 
	        @RequestPart(value = "bFileName", required = false) MultipartFile bFile, 
			@RequestPart(value = "bCoverFileName", required = false) MultipartFile bCoverFile) 
	        throws Exception {
		
	    boardService.create(boardDTO, bFile, bCoverFile, jwtToken);
	    
	    return ResponseEntity.ok().build();
	}

	// 글 수정
//	@PutMapping("/portfolio/edit/{board_id}")
	@PutMapping(value = "/portfolio/edit/{board_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> editBoard(
			@RequestPart(value = "board") BoardDTO boardDTO,
	        @PathVariable("board_id") Long boardId,
	        @RequestHeader("Authorization") String jwtToken, 
	        @RequestPart(value = "bFileName", required = false) MultipartFile bFile, 
			@RequestPart(value = "bCoverFileName", required = false) MultipartFile bCoverFile) 
	        throws Exception {
		
	    boardDTO.setBoardId(boardId);
	    boardService.edit(boardDTO, bFile, bCoverFile, jwtToken);
	    
	    return ResponseEntity.ok().build();
	}
	
	// 글 삭제
//	@DeleteMapping("/portfolio/delete/{board_id}")
	@DeleteMapping(value = "/portfolio/delete/{board_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> deleteBoard(
			@PathVariable("board_id") Long boardId,
	        @RequestHeader("Authorization") String jwtToken, 
	        @RequestPart(value = "bFileName", required = false) MultipartFile bFile, 
			@RequestPart(value = "bCoverFileName", required = false) MultipartFile bCoverFile)
	        throws Exception {
		
	    boardService.delete(boardId, bFile, bCoverFile, jwtToken);
	    return ResponseEntity.ok().build();
	}
	
	//======================== 댓글 ========================
	
	// 댓글 등록
	@PostMapping("/portfolio/{board_id}")
	public ResponseEntity<?> registerComment(
			@RequestBody B_commentsDTO commentDTO,
			@PathVariable("board_id") Long boardId,
			@RequestHeader("Authorization") String jwtToken) {
		
		CommentResponse commentRes = boardService.createComment(commentDTO, boardId, jwtToken);
		
		return ResponseEntity.ok(commentRes);
	}
	
	// 댓글 수정
	@PutMapping("/portfolio/comment/edit/{comment_id}")
	public ResponseEntity<?> editComment(
			@RequestBody B_commentsDTO commentDTO,
			@PathVariable("comment_id") Long commentId,
			@RequestHeader("Authorization") String jwtToken) {
		
		CommentResponse commentRes = boardService.editComment(commentDTO, commentId, jwtToken);

		return ResponseEntity.ok(commentRes);
	}
	
	// 댓글 삭제
	@DeleteMapping("/portfolio/comment/delete/{comment_id}")
	public ResponseEntity<?> deleteComment(
			@PathVariable("comment_id") Long commentId,
			@RequestHeader("Authorization") String jwtToken) {
		
		boardService.deleteComment(commentId, jwtToken);
		
		return ResponseEntity.ok().build();
	}
	
	//======================== 좋아요 ========================
	
	// 좋아요
	@PostMapping("/like/{board_id}")
	public ResponseEntity<?> like(@RequestHeader("Authorization") String jwtToken, @PathVariable("board_id") Long boardId) throws Exception {
		Member authenticatedMember = commonService.getAuthenticatedMember(jwtToken).orElseThrow(UnauthorizedException::new);
		String email = authenticatedMember.getEmail();
		boolean isLiked = boardService.isUserLiked(email, boardId);
		
		Board board;
		if(isLiked) {
			boardService.delete(email, boardId);
			board = boardService.updateLike(boardId, false);
			
		} else {
			boardService.insert(email, boardId);
			board = boardService.updateLike(boardId, true);
		}
		int updatedLikeCount = board.getBLike();
		return ResponseEntity.ok(updatedLikeCount);
	}
	
	//======================== 조회수 ========================

	// 조회수
	@PostMapping("/{board_id}")
	public ResponseEntity<?> click(@PathVariable("board_id") Long boardId) throws NotFoundException{
		boardService.updateCnt(boardId);
		Board board = boardRepo.findById(boardId).orElseThrow(NotFoundException::new);
		int updatedReadCount = board.getBCnt();
		
		return ResponseEntity.ok(updatedReadCount);
	}
	
	//======================== 검색 ========================
	
	// 검색
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String keyword) {
		List<Object> result = commonService.search(keyword);
		return ResponseEntity.ok(result);
	}
}
