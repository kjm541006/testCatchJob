package com.project.catchJob.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.catchJob.domain.community.CommunityPost;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long>{

}
