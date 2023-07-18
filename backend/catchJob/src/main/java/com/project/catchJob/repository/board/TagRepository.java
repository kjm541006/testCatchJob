package com.project.catchJob.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.catchJob.domain.board.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
