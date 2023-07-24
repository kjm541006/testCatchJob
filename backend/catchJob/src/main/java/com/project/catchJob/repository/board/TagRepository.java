package com.project.catchJob.repository.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.catchJob.domain.board.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Tag findByTagId(Long tagId);
	Optional<Tag> findByTagName(String name);
}
