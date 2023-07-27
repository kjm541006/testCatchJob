// ProjectService.java
package com.project.catchJob.service;

import java.util.List;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.project.ProjectDTO;

public interface ProjectService {

	Project addProject(ProjectDTO projectDTO, String userEmail);
	List<Project> getAllProjects();
}
