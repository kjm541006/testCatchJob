package com.project.catchJob.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.catchJob.domain.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	
}
