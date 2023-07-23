package com.project.catchJob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.board.B_likeRepository;
import com.project.catchJob.security.TokenProvider;
import com.project.catchJob.service.BoardService;
import com.project.catchJob.service.CommonService;
import com.project.catchJob.service.MemberService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class BoardController {
	
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
	public ResponseEntity<List<BoardDTO>> getBoardList(@RequestBody Member member) {
		List<BoardDTO> boardDTOList = boardService.getBoardList(member);
		return ResponseEntity.ok(boardDTOList);
	}
	
	// 글 등록
//	@PostMapping("/portfolio/register")
//	public ResponseEntity<?> registerBoard(@RequestBody BoardDTO boardDTO, @RequestBody MemberDTO memberDTO, @RequestParam("bFileName") MultipartFile file, @RequestParam("bCoverFileName") MultipartFile coverFile) throws Exception {
//		if(boardDTO.getBoardId() != null) {
//			throw new Exception("게시글이 이미 존재합니다!");
//		}
//		BoardDTO responseBoardDTO = BoardDTO.builder()
//				.bTitle(boardDTO.getBTitle())
//				.bContents(boardDTO.getBContents())
//				.bFileName(boardDTO.getBFileName())
//				.bCoverFileName(boardDTO.getBCoverFileName())
//				.member(memberDTO)
//				.tags(boardDTO.getTags())
//				.build();
//		boardService.create(responseBoardDTO, memberDTO, file, coverFile);
//		return ResponseEntity.ok().body(responseBoardDTO);
//	}
	
	@PostMapping("/portfolio/register")
	public ResponseEntity<?> registerBoard(
	        @RequestBody BoardDTO boardDTO, 
	        @RequestHeader("Authorization") String jwtToken, 
	        @RequestParam("bFileName") MultipartFile file, 
	        @RequestParam("bCoverFileName") MultipartFile coverFile) throws Exception {

	    Member authenticatedMember = commonService.getAuthenticatedMember(jwtToken).orElseThrow(UnauthorizedException::new);

	    MemberDTO memberDTO = MemberDTO.toMemberDTO(authenticatedMember);
	    boardService.create(boardDTO, memberDTO, file, coverFile);

	    return ResponseEntity.ok().build();
	}


	// 글 수정
//	@PostMapping("/portfolio/update/{board_id}")
	
//	
//	// 글 조회
//	@GetMapping("/portfolio/{board_id}")
//	public ResponseEntity<BoardDTO> getBoard(@PathVariable Long board_id) {
//		try {
//			Board board = boardService.getBoard(board_id);
//			Member member = memberService.getMember(board.getMember().getMemberId());
//			
//			String fileUrlPath = boardService.getFileUrlPath();
//			BoardDTO boardDTO =  BoardDTO.toDTO(board, member, bLikeRepo, fileUrlPath);
//			return ResponseEntity.ok(boardDTO);
//		} catch (Exception e) {
//			// 게시글이 없는 경우 404에러
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
//	}
	
	
	
	@PostMapping("/like")
	public ResponseEntity<?> insert(@RequestParam Long memberId, @RequestParam Long boardId) throws Exception {
		boardService.insert(memberId, boardId);
		return ResponseEntity.ok("하트 ++");
	}
	
	@DeleteMapping("/unlike")
	public ResponseEntity<?> delete(@RequestParam Long memberId, @RequestParam Long boardId) throws Exception {
		boardService.delete(memberId, boardId);
		return ResponseEntity.ok("하트 --");
	}
	
}
