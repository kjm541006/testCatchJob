package com.project.catchJob.service;

import com.project.catchJob.domain.community.CommunityPost;
import com.project.catchJob.repository.community.CommunityPostRepository;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class CommunityService {

	private final CommunityPostRepository communityPostRepository;
	
	public CommunityService(CommunityPostRepository communityPostRepository) {
		this.communityPostRepository = communityPostRepository;
	}
	
	public List<CommunityPost> getAllPosts(){
		return communityPostRepository.findAll();
		
	}
	
	public CommunityPost createPost(CommunityPost post) {
		return communityPostRepository.save(post);
	}
}
