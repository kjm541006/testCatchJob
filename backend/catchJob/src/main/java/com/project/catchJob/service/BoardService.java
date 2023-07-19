package com.project.catchJob.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.catchJob.domain.board.B_like;
import com.project.catchJob.domain.board.B_tag;
import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.board.Tag;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.board.B_likeDTO;
import com.project.catchJob.dto.board.BoardDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.board.B_commentsRepository;
import com.project.catchJob.repository.board.B_likeRepository;
import com.project.catchJob.repository.board.BoardRepository;
import com.project.catchJob.repository.member.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private B_commentsRepository bCommRepo; // 댓글
	
	@Autowired
	private B_likeRepository bLikeRepo; // 좋아요
	
	
	// 글 목록
	public List<BoardDTO> getBoardList(Member member) {
		List<Board> boards = boardRepo.findAll();	
		return boards.stream()
				.map(board -> BoardDTO.toDTO(board, member, bLikeRepo)) // member, bLikeRepo 전달
				.collect(Collectors.toList());
	}
	
	
	// 글 등록
    public void create(BoardDTO boardDTO, MemberDTO memberDTO) {
       
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
        
        boardRepo.save(board);
        
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
