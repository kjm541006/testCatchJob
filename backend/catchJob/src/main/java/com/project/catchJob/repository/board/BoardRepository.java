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
    @Query("update Board b set b.bCnt = b.bCnt + 1 where b.boardId = :boardId")
    int updateCnt(@Param("boardId")Long boardId);
	
	@Modifying
	@Transactional
	@Query("update Board b set b.bLike = b.bLike + :increment where b.boardId = :boardId")
	int updateLike(@Param("boardId") Long boardId, @Param("increment") int increment);

	Board findByBoardId(Long boardId);
}
