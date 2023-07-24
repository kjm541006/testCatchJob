// ProjectService.java
package com.project.catchJob.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.project.ProjectDTO;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.repository.project.ProjectRepository;

public interface ProjectService {

	Project addProject(ProjectDTO projectDTO, String userEmail);
}
