package com.project.catchJob.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.catchJob.domain.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Board findByBoardId(Long boardId);
}
