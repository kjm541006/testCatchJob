package com.project.catchJob.repository.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.catchJob.domain.community.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {

	@Modifying
	@Transactional
	@Query("update Community c set c.cLike = c.cLike + 1 where c.communityId = :communityId")
	int updateCommunityLike(@Param("communityId")Long communityId);
	
	Page<Community> findByCTypeContaining(String cType, Pageable pageable);
}
