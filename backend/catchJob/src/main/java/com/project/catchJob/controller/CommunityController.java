package com.project.catchJob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.catchJob.domain.community.Comment;
import com.project.catchJob.domain.community.CommunityPost;
import com.project.catchJob.service.CommunityService;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping
    public List<CommunityPost> getAllPosts() {
        return communityService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public CommunityPost getPostById(@PathVariable Long postId) {
        return communityService.getPostById(postId);
    }

    @PostMapping
    public CommunityPost createPost(@RequestBody CommunityPost post) {
        return communityService.createPost(post);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addCommentToPost(@PathVariable Long postId, @RequestBody Comment comment) {
        Comment addedComment = communityService.addCommentToPost(postId, comment);
        return ResponseEntity.ok(addedComment);
    }
}