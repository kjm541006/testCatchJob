package com.project.catchJob.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.domain.community.Comment;
import com.project.catchJob.domain.community.CommunityPost;
import com.project.catchJob.repository.community.CommentRepository;
import com.project.catchJob.repository.community.CommunityPostRepository;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	private final CommentRepository commentRepository;
	private final CommunityPostRepository communityPostRepositroy;
	
	@Autowired
	public CommentController(CommentRepository commentRepository, CommunityPostRepository communityPostRepositroy) {
		this.commentRepository = commentRepository;
		this.communityPostRepositroy = communityPostRepositroy;
	}
	
	@PostMapping("/{postId}")
	public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment){
		Optional<CommunityPost> postOptional = communityPostRepositroy.findById(postId);
		if(postOptional.isPresent()) {
			CommunityPost post = postOptional.get();
			comment.setPost(post);
			Comment savedComment = commentRepository.save(comment);
			return ResponseEntity.ok(savedComment);
			
		}else {
			return ResponseEntity.notFound().build();
		}
	}

}
