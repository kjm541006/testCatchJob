package com.project.catchJob.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.catchJob.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Boolean existsByEmail(String email);
	Member findByEmail(String email);
}
