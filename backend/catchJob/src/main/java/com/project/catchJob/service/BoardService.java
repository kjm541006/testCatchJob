package com.project.catchJob.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.catchJob.domain.board.B_like;
import com.project.catchJob.domain.board.Board;
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
	
	// 글 목록 조회
	public Page<BoardDTO> getBoardList(BoardDTO boardDTO, PageRequest pageReq) {
		Page<Board> boardPage = boardRepo.findAll(pageReq);	
		return boardPage.map(board -> {
			Member member = memberRepo.findById(board.getMember().getMemberId()).orElse(null);
			return BoardDTO.toBoardDTO(board, member);
		});
	}
/*	
	// 글 조회
	public Optional<Board> getBoard(Board board) {
		Optional<Board> findBoard = boardRepo.findById(board.getBoardId());
		if(findBoard.isPresent()) return findBoard;
		return findBoard;
	}
*/	
	// 게시글 조회
    public Board getBoard(Long id) throws Exception {
        return boardRepo.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }
	
	// 글 작성
    public void create(BoardDTO boardDTO, MemberDTO memberDTO) {
        if(boardRepo.existsById(boardDTO.getBoardId())) {
            throw new RuntimeException("이미 존재하는 게시글입니다!");
        } else {
            Member member = memberRepo.findByEmail(memberDTO.getEmail());
                    

            Board board = Board.builder()
                    .bTitle(boardDTO.getBTitle())
                    .bContents(boardDTO.getBContents())
                    .bFileName(boardDTO.getBFileName())
                    .bUploadFile(boardDTO.getBUploadFile())
                    .bCoverUploadFile(boardDTO.getBCoverUploadFile())
                    .bCoverFileName(boardDTO.getBCoverFileName())
                    .member(member)
                    .build();

            boardRepo.save(board);
        }
    }
	/*
	// 좋아요
	@Transactional
	public void insert(B_likeDTO bLikeDTO) throws Exception {
		Member member = memberRepo.findById(bLikeDTO.getMemberId())
				.orElseThrow(() -> new NotFoundException());  
		
		Board board = boardRepo.findById(bLikeDTO.getBoardId())
				.orElseThrow(() -> new NotFoundException());
				
		// 이미 좋아요 되어 있으면 에러
		if(bLikeRepo.findByMemberAndBoard(member, board).isPresent()) {
			throw new Exception();
		}
	
		B_like like = B_like.builder()
				.board(board)
				.member(member)
				.build();
		
		bLikeRepo.save(like);
	}
	
	// 좋아요 취소
	@Transactional
	public void delete(B_likeDTO bLikeDTO) throws NotFoundException {
		Member member = memberRepo.findById(bLikeDTO.getMemberId())
				.orElseThrow(() -> new NotFoundException());  
		
		Board board = boardRepo.findById(bLikeDTO.getBoardId())
				.orElseThrow(() -> new NotFoundException());
		
		B_like like = bLikeRepo.findByMemberAndBoard(member, board)
				.orElseThrow(() -> new NotFoundException());
		
		bLikeRepo.delete(like);
	}
	
	// 좋아요 +1
	public void updateLike(Board board, boolean b) {
		
		if(b) {
			board.setBLike(board.getBCnt() + 1);
		} else {
			board.setBLike(board.getBCnt() - 1);
		}
		boardRepo.save(board);
	}
	*/
	
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
