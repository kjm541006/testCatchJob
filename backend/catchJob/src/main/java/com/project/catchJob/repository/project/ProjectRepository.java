package com.project.catchJob.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catchJob.domain.project.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {

	
}
