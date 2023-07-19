package com.project.catchJob.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.catchJob.domain.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	@Modifying
    @Transactional
    @Query("update Board b set b.b_cnt = b.b_cnt + 1 where b.board_id = :board_id")
    int updateReadCount(@Param("board_id")Long seq);
	
	Board findByBoardId(Long boardId);
}
