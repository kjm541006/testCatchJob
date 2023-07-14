package com.project.catchJob.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catchJob.domain.community.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
