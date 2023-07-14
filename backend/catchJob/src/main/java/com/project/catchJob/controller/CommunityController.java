package com.project.catchJob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.service.CommunityService;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
	
	private final CommunityService communityService;
	
	@Autowired
	public CommunityController(CommunityService communityServce) {
		this.communityService = communityService;
	}
	
	@GetMapping
	public List<CommunityPost> getAllpost(){
		return communityService.getAllPosts();
	}
	
	public CommunityPostController createPost(@RequestBody CommunityPostController post) {
		return communityService.createPost(post);
	}
}
