package com.project.catchJob.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.board.B_comments;
import com.project.catchJob.domain.board.B_like;
//import com.project.catchJob.domain.board.B_tag;
import com.project.catchJob.domain.board.Board;
//import com.project.catchJob.domain.board.Tag;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.B_commentsDTO;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.board.CommentResponse;
import com.project.catchJob.dto.board.TagDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.exception.UnauthorizedException;
import com.project.catchJob.repository.board.B_commentsRepository;
import com.project.catchJob.repository.board.B_likeRepository;
//import com.project.catchJob.repository.board.B_tagRepository;
import com.project.catchJob.repository.board.BoardRepository;
//import com.project.catchJob.repository.board.TagRepository;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.security.TokenProvider;

@Service
public class BoardService {
	
	@Value("${file.path}") private String filePath;
	private final CommonService commonService;
	private final List<TagDTO> tagDTOList;
	
	@Autowired
	public BoardService(CommonService commonService) {
		this.commonService = commonService;
		this.tagDTOList = new ArrayList<>();
	}
	
	@Autowired private MemberRepository memberRepo;
	@Autowired private BoardRepository boardRepo;
	@Autowired private B_commentsRepository bCommRepo; // 댓글
	@Autowired private B_likeRepository bLikeRepo; // 좋아요
	@Autowired private TokenProvider tokenProvider;
	
//	private String uploadFolderPath = "catchJob/upload/"; 
	private String uploadFolderPath = "D:ding/";
	// 서버 실제 디렉토리 구조
//	private String fileUrlPath = "/upload/";
	private String fileUrlPath = "D:ding/img/";
	// 사용자가 파일에 액세스하는 경우 필요한 url 경로
	// 경로를 구분해서 사용하면 서버와 클라이언트 간의 엑세스 권한 분리해서 관리 가능
	
	public String getFileUrlPath() {
		return fileUrlPath;
	}
	
	// 글 목록
	public List<BoardDTO> getBoardList(String jwtToken) {
		List<Board> boards = boardRepo.findAll();	
	
		// 로그인한 사용자
		if(jwtToken != null) {
			Member member = commonService.getAuthenticatedMember(jwtToken)
					.orElseThrow(UnauthorizedException::new);
			return boards.stream()
					.map(board -> BoardDTO.toDTO(board, member, bLikeRepo, fileUrlPath)) // member, bLikeRepo 전달
					.collect(Collectors.toList());
		}
		
		// 로그인하지 않은 사용자
		return boards.stream()
				.map(board -> BoardDTO.toDTOWithoutMember(board, filePath))
				.collect(Collectors.toList());
	}

	// 글 등록
	public void create(BoardDTO boardDTO, MemberDTO memberDTO, MultipartFile file) {

	    String jwtToken = memberDTO.getToken();
	    Optional<Member> optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken);

	    Member member = memberRepo.findByEmail(memberDTO.getEmail());

	    Board board = Board.builder()
	            .bTitle(boardDTO.getBTitle())
	            .bContents(boardDTO.getBContents())
	            .tags(boardDTO.getTags())
	            .member(member)
	            .build();

	    // 파일 저장
	    if(file != null && !file.isEmpty()) {
	        String fileName = saveFile(file);
	        board.setBFileName(fileName);
	    }

	    boardRepo.save(board);
	}
    
    // 파일 저장
    public String saveFile(MultipartFile file) {
    	try {
	    	// 저장 경로 지정
	    	//String savePath = new File("").getAbsolutePath() + "/src/main/resources/static/upload/";
    		String savePath = new File(uploadFolderPath).getAbsolutePath() + "/";
    		File dir = new File(savePath);
    		if(!dir.exists()) {
    			dir.mkdir(); // 폴더 없다면 폴더 생성
    		}
	    	// 파일 이름 가져옴
	    	String originalFileName = file.getOriginalFilename();
	    	// 저장될 파일 이름 설정
	    	String storedFileName = UUID.randomUUID() + "_" + originalFileName;
	    	// 지정된 경로에 파일 저장
	    	File finFile = new File(savePath + storedFileName);
			file.transferTo(finFile);
			
			// 저장된 파일명 반환
			return storedFileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
    }
    
    // 글 수정
    public void update(BoardDTO boardDTO, MemberDTO memberDTO, MultipartFile file) {
    	
    	String jwtToken = memberDTO.getToken();
	    Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
	    		.orElseThrow(UnauthorizedException::new);
	    System.out.println("-=-------" + boardDTO);
	    Board board = boardRepo.findById(boardDTO.getBoardId())
	    		.orElseThrow(() -> new EntityNotFoundException("게시글이 없음"));
	    
	    if(!optAuthenticatedMember.getEmail().equals(board.getMember().getEmail())) {
	    	throw new UnauthorizedException();
	    }
	    
	    board.setBTitle(boardDTO.getBTitle());
	    board.setBContents(boardDTO.getBContents());
	    board.setTags(boardDTO.getTags());
	 
    	// 파일 저장
	    if(file != null && !file.isEmpty()) {
	    	String fileName = saveFile(file);
	    	board.setBFileName(fileName);
	    }
	    boardRepo.save(board);
    }
    
    // 글 삭제
    public void delete(Long boardId, MemberDTO memberDTO, MultipartFile file) {
    	
    	String jwtToken = memberDTO.getToken();
	    Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
	    		.orElseThrow(UnauthorizedException::new);

	    Board board = boardRepo.findById(boardId)
	    		.orElseThrow(() -> new EntityNotFoundException("게시글이 없음"));
	    
	    if(!optAuthenticatedMember.getEmail().equals(board.getMember().getEmail())) {
	    	throw new UnauthorizedException();
	    }
	    
	    boardRepo.deleteById(boardId);
    }

    //======================== 댓글 ========================
    
    // 댓글 등록
    public CommentResponse createComment(B_commentsDTO commentDTO, MemberDTO memberDTO, Long boardId) {
    	String jwtToken = memberDTO.getToken();
    	Optional<Member> optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken);
    	
    	Member member = memberRepo.findByEmail(memberDTO.getEmail());
    	
    	Board board = boardRepo.findById(boardId).orElseThrow(() -> new EntityNotFoundException("게시글이 없음"));
    	B_comments comments = B_comments.builder()
    			.bComContent(commentDTO.getCommentContent())
    			.member(member)
    			.board(board)
    			.build();
    	B_comments saveComm = bCommRepo.save(comments);
    	bCommRepo.flush();
    	return new CommentResponse(saveComm.getBComDate());
    }
    
    // 댓글 수정
    public CommentResponse editComment(B_commentsDTO commentDTO, MemberDTO memberDTO, Long commentId) {
    	
    	String jwtToken = memberDTO.getToken();
    	Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
    			.orElseThrow(UnauthorizedException::new);
    	
    	B_comments comment = bCommRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글이 없음"));
    	if (!optAuthenticatedMember.getEmail().equals(comment.getMember().getEmail())) {
    		throw new UnauthorizedException();
    	}
    	comment.setBComContent(commentDTO.getCommentContent());
    	comment.setBComDate(LocalDateTime.now());
    	B_comments saveComm = bCommRepo.save(comment);
    	bCommRepo.flush();
    	return new CommentResponse(saveComm.getBComDate());
    }
    
    // 댓글 삭제
    public void deleteComment(MemberDTO memberDTO, Long commentId) {
    	
    	String jwtToken = memberDTO.getToken();
    	Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
    			.orElseThrow(UnauthorizedException::new);
    	
    	B_comments comment = bCommRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글이 없음"));
    	
    	if (!optAuthenticatedMember.getEmail().equals(comment.getMember().getEmail())) {
    		throw new UnauthorizedException();
    	}
    	bCommRepo.deleteById(commentId);
    }
    
    //======================== 좋아요 ========================
    
    // 좋아요 확인
    public boolean findLike(String email, Long boardId) throws Exception {
    	Member member = memberRepo.findOptionalByEmail(email)
	            .orElseThrow(() -> new NotFoundException());

	    Board board = boardRepo.findById(boardId)
	            .orElseThrow(() -> new NotFoundException());
	   
	    Optional<B_like> like = bLikeRepo.findByMemberAndBoard(member, board);
	    return like.isPresent();
    }
    
    
	// 좋아요 추가
	public void insert(String email, Long boardId) throws Exception {
	    Member member = memberRepo.findOptionalByEmail(email)
	            .orElseThrow(() -> new NotFoundException());

	    Board board = boardRepo.findById(boardId)
	            .orElseThrow(() -> new NotFoundException());
//
//	    // 이미 좋아요 되어 있으면 에러
//	    if (bLikeRepo.findByMemberAndBoard(member, board).isPresent()) {
//	        throw new Exception();
//	    }

	    B_like like = B_like.builder()
	            .board(board)
	            .member(member)
	            .build();

	    bLikeRepo.save(like);
	}

	// 좋아요 취소
	public void delete(String email, Long boardId) throws NotFoundException {
	    Member member = memberRepo.findOptionalByEmail(email)
	            .orElseThrow(() -> new NotFoundException());

	    Board board = boardRepo.findById(boardId)
	            .orElseThrow(() -> new NotFoundException());

	    B_like like = bLikeRepo.findByMemberAndBoard(member, board)
	            .orElseThrow(() -> new NotFoundException());

	    bLikeRepo.delete(like);
	}

	// 좋아요 수 업데이트
	public void updateLike(Board board, boolean b) {
	    if (b) {
	        board.setBLike(board.getBLike() + 1);
	    } else {
	        board.setBLike(board.getBLike() - 1);
	    }
	    boardRepo.save(board);
	}


}
