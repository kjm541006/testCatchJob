package com.project.catchJob.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.board.B_like;
import com.project.catchJob.domain.board.B_tag;
import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.board.Tag;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.board.B_commentsRepository;
import com.project.catchJob.repository.board.B_likeRepository;
import com.project.catchJob.repository.board.BoardRepository;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.security.TokenProvider;

@Service
public class BoardService {
	
	
	@Autowired
	public BoardService(CommonService commonService) {
		this.commonService = commonService;
	}
	@Autowired private CommonService commonService;
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
	public List<BoardDTO> getBoardList(Member member) {
		List<Board> boards = boardRepo.findAll();	
		return boards.stream()
				.map(board -> BoardDTO.toDTO(board, member, bLikeRepo, fileUrlPath)) // member, bLikeRepo 전달
				.collect(Collectors.toList());
	}
	
	
	// 글 등록
    public void create(BoardDTO boardDTO, MemberDTO memberDTO, MultipartFile file, MultipartFile coverFile) {
       

    	String jwtToken = memberDTO.getToken();
    	Optional<Member> optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken);
    			
        Member member = memberRepo.findByEmail(memberDTO.getEmail()); 

        Board board = Board.builder()
                .bTitle(boardDTO.getBTitle())
                .bContents(boardDTO.getBContents())
                .bFileName(boardDTO.getBFileName())
                .bCoverFileName(boardDTO.getBCoverFileName())
                .member(member)
                .build();
        
        // 태그 정보 저장
        if(boardDTO.getTags() != null && !boardDTO.getTags().isEmpty()) {
        	List<B_tag> tagList = boardDTO.getTags().stream()
        			.map(tagDTO -> {
        				B_tag bTag = new B_tag();
        				bTag.setBoard(board); // Tag 생성 및 설정
        				Tag tag = new Tag();
        				tag.setTagName(tagDTO.getTagName());
        				bTag.setTag(tag);
        				return bTag;
        			})
        			.collect(Collectors.toList());
        	board.setBoardTagList(tagList);
        }
        
        // 파일 저장
        if(file != null && !file.isEmpty()) {
        	String fileName = saveFile(file);
        	board.setBFileName(fileName);
        }
        
        // 커버 파일 저장
        if(coverFile != null && !coverFile.isEmpty()) {
        	String coverFileName = saveFile(coverFile);
        	board.setBCoverFileName(coverFileName);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
    }
    
	// 글 조회
    public Board getBoard(Long boardId) throws Exception {
        return boardRepo.findById(boardId)
                .orElseThrow(() -> new Exception("해당 게시글이 없습니다"));
    }
	
    
	// 좋아요 추가
	public void insert(Long memberId, Long boardId) throws Exception {
	    Member member = memberRepo.findById(memberId)
	            .orElseThrow(() -> new NotFoundException());

	    Board board = boardRepo.findById(boardId)
	            .orElseThrow(() -> new NotFoundException());

	    // 이미 좋아요 되어 있으면 에러
	    if (bLikeRepo.findByMemberAndBoard(member, board).isPresent()) {
	        throw new Exception();
	    }

	    B_like like = B_like.builder()
	            .board(board)
	            .member(member)
	            .build();

	    bLikeRepo.save(like);
	}

	// 좋아요 취소
	public void delete(Long memberId, Long boardId) throws NotFoundException {
	    Member member = memberRepo.findById(memberId)
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
