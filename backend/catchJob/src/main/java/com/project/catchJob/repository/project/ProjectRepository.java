package com.project.catchJob.repository.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.catchJob.domain.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	//@Query("select p from Project p where p.title like CONCAT('%', :keyword, '%')")
	List<Project> findByTitleContaining(String keyword);
	List<Project> findByMemberNameContaining(String keyword);
}
