package com.project.catchJob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catchJob.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
