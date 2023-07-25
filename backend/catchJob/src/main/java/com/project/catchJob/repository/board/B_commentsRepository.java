package com.project.catchJob.repository.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.catchJob.domain.board.B_comments;

public interface B_commentsRepository extends JpaRepository<B_comments, Long> {

}
