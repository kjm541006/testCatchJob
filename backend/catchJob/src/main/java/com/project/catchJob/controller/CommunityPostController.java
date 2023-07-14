package com.project.catchJob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.domain.community.CommunityPost;
import com.project.catchJob.repository.community.CommunityPostRepository;

@RestController
@RequestMapping("/api/posts")
public class CommunityPostController {
	private final CommunityPostRepository communityPostRepositroy;
	
	@Autowired
	public CommunityPostController(CommunityPostRepository communityPostRepository) {
		this.communityPostRepositroy = communityPostRepository;
	}
	
	@PostMapping
	public CommunityPost createPost(@RequestBody CommunityPost post) {
		return communityPostRepository.save(post);
	}

}
