package com.project.catchJob.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.community.Comment;
import com.project.catchJob.domain.community.CommunityPost;
import com.project.catchJob.repository.community.CommunityPostRepository;

import java.util.List;

@Service
public class CommunityService {
    private final CommunityPostRepository postRepository;

    @Autowired
    public CommunityService(CommunityPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<CommunityPost> getAllPosts() {
        return postRepository.findAll();
    }

    public CommunityPost getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public CommunityPost createPost(CommunityPost post) {
        return postRepository.save(post);
    }

    public Comment addCommentToPost(Long postId, Comment comment) {
        CommunityPost post = getPostById(postId);
        post.addComment(comment);
        postRepository.save(post);
        return post.getComments()              
                .stream()
                .filter(c -> c.equals(comment))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to add comment to post"));
    }
}
