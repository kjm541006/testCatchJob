package com.project.catchJob.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	@PersistenceContext private EntityManager entityManager;
	
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
//					.map(board -> BoardDTO.toDTO(board, member, bLikeRepo, fileUrlPath)) // member, bLikeRepo 전달
					.map(board -> BoardDTO.toDTO(board, member, this, filePath)) // member, bLikeRepo 전달
					.collect(Collectors.toList());
		}
		
		// 로그인하지 않은 사용자
		return boards.stream()
				.map(board -> BoardDTO.toDTOWithoutMember(board, filePath))
				.collect(Collectors.toList());
	}

	// 글 등록
	public void create(String bTitle, String bContents, List<String> tags, MultipartFile bFile, MultipartFile bCoverFile, String jwtToken) {

	    Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
	    		.orElseThrow(UnauthorizedException::new);
	    Member member = memberRepo.findByEmail(optAuthenticatedMember.getEmail());

	    Board board = Board.builder()
	            .bTitle(bTitle)
	            .bContents(bContents)
	            .tags(tags)
	            .member(member)
	            .build();

	    // 파일 저장
	    if(bFile != null && !bFile.isEmpty()) {
	        String fileName = saveFile(bFile);
	        board.setBFileName(fileName);
	    }
	    if(bCoverFile != null && !bCoverFile.isEmpty()) {
	    	String fileName = saveFile(bCoverFile);
	    	board.setBCoverFileName(fileName);
	    }
	    boardRepo.save(board);
	}
	
	// 작동가능
//	public void create(BoardDTO boardDTO, MultipartFile bFile, MultipartFile bCoverFile, String jwtToken) {
//		
//		Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
//				.orElseThrow(UnauthorizedException::new);
//		Member member = memberRepo.findByEmail(optAuthenticatedMember.getEmail());
//		
//		Board board = Board.builder()
//				.bTitle(boardDTO.getBTitle())
//				.bContents(boardDTO.getBContents())
//				.tags(boardDTO.getTags())
//				.member(member)
//				.build();
//		
//		// 파일 저장
//		if(bFile != null && !bFile.isEmpty()) {
//			String fileName = saveFile(bFile);
//			board.setBFileName(fileName);
//		}
//		if(bCoverFile != null && !bCoverFile.isEmpty()) {
//			String fileName = saveFile(bCoverFile);
//			board.setBCoverFileName(fileName);
//		}
//		boardRepo.save(board);
//	}
    
    // 파일 저장
    public String saveFile(MultipartFile file) {
    	try {
	    	// 저장 경로 지정
	    	//String savePath = new File("").getAbsolutePath() + "/src/main/resources/static/upload/";
//    		String savePath = new File(filePath).getAbsolutePath() + "/";
    		String savePath = filePath + "/";
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
    public void edit(BoardDTO boardDTO, MultipartFile bFile, MultipartFile bCoverFile, String jwtToken) {
    	
	    Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
	    		.orElseThrow(UnauthorizedException::new);
	    
	    Board board = boardRepo.findById(boardDTO.getBoardId())
	    		.orElseThrow(() -> new EntityNotFoundException("게시글이 없음"));
	    
	    if(!optAuthenticatedMember.getEmail().equals(board.getMember().getEmail())) {
	    	throw new UnauthorizedException();
	    }
	    
	    board.setBTitle(boardDTO.getBTitle());
	    board.setBContents(boardDTO.getBContents());
	    board.setTags(boardDTO.getTags());
	    board.setBFileName(boardDTO.getBFileName());
	    board.setBCoverFileName(boardDTO.getBCoverFileName());
	 
//	    // 파일 삭제
//	    if(boardDTO.isRemoveBFile()) {
	    
    	// 파일 저장
	    if(bFile != null && !bFile.isEmpty()) {
	    	String fileName = saveFile(bFile);
	    	board.setBFileName(fileName);
	    } else {
	    	String fileName = saveFile(bFile);
	    	deleteFile(fileName);
	    }
	    if(bCoverFile != null && !bCoverFile.isEmpty()) {
	    	String fileName = saveFile(bCoverFile);
	    	board.setBCoverFileName(fileName);
	    } else {
	    	String fileName = saveFile(bFile);
	    	deleteFile(fileName);
	    }
	    
	    boardRepo.save(board);
    }
    
    // 글 삭제
    public void delete(Long boardId, MultipartFile bFile, MultipartFile bCoverFile, String jwtToken) {
    	
	    Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
	    		.orElseThrow(UnauthorizedException::new);

	    Board board = boardRepo.findById(boardId)
	    		.orElseThrow(() -> new EntityNotFoundException("게시글이 없음"));
	    
	    if(!optAuthenticatedMember.getEmail().equals(board.getMember().getEmail())) {
	    	throw new UnauthorizedException();
	    }
	    
	    if(bFile != null && !bFile.isEmpty()) {
	    	String fileName = board.getBFileName();
	    	deleteFile(fileName);
	    }
	    if(bCoverFile != null && !bCoverFile.isEmpty()) {
	    	String fileName = board.getBCoverFileName();
	    	deleteFile(fileName);
	    }
	    boardRepo.deleteById(boardId);
    }
    
    // 파일 삭제
    public void deleteFile(String fileName) {
    	File file = new File(filePath + fileName);
    	if(file.exists()) {
    		file.delete();
    	}
    }

    //======================== 댓글 ========================
    
    // 댓글 등록
    public CommentResponse createComment(B_commentsDTO commentDTO, Long boardId, String jwtToken) {
	    
    	Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
	    		.orElseThrow(UnauthorizedException::new);
	    
    	Member member = memberRepo.findByEmail(optAuthenticatedMember.getEmail());
    	
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
    public CommentResponse editComment(B_commentsDTO commentDTO, Long commentId, String jwtToken) {
    	
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
    public void deleteComment(Long commentId, String jwtToken) {
    	
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
    public boolean isUserLiked(String email, Long boardId) {
    	Member member = memberRepo.findOptionalByEmail(email).orElse(null);
    	if(member == null) {
    		return false;
    	} 
    	
    	Board board = boardRepo.findById(boardId).orElse(null);
    	if(board == null) {
    		return false;
    	}
    	
    	Optional<B_like> like = bLikeRepo.findByMemberAndBoard(member, board);
    	return like.isPresent();
    }
  
	// 좋아요 추가
	public void insert(String email, Long boardId) throws Exception {
	    Member member = memberRepo.findOptionalByEmail(email)
	            .orElseThrow(() -> new NotFoundException());

	    Board board = boardRepo.findById(boardId)
	            .orElseThrow(() -> new NotFoundException());

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
	@Transactional
	public Board updateLike(Long boardId, boolean b) throws NotFoundException {
		int increment = b ? 1 : -1;
	    boardRepo.updateLike(boardId, increment);
	    entityManager.flush();
	    entityManager.clear();
	    Board board = boardRepo.findById(boardId).orElseThrow(NotFoundException::new);
		
	    return board;
	}

	//======================== 조회수 ========================
	
	// 조회수 업데이트
	public int updateCnt(Long boardId) throws NotFoundException {
		boardRepo.updateCnt(boardId);
		Board board = boardRepo.findById(boardId).orElseThrow(NotFoundException::new);
		return board.getBCnt();
	}
}
