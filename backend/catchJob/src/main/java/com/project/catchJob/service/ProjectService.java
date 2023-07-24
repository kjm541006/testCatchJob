// ProjectService.java
package com.project.catchJob.service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.project.ProjectDTO;

public interface ProjectService {

	Project addProject(ProjectDTO projectDTO);
	
	Project createProjectFromDTO(ProjectDTO projectDTO, Member member);
}
