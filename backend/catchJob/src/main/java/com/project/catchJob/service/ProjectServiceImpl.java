package com.project.catchJob.service;

import org.springframework.stereotype.Service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.project.ProjectDTO;
import com.project.catchJob.repository.project.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
	
	private final ProjectRepository projectRepository;

	@Override
	public Project addProject(ProjectDTO projectDTO) {
		Project project = new Project();


	    projectRepository.save(project);
	    return project;
	}

	@Override
	public Project createProjectFromDTO(ProjectDTO projectDTO, Member member) {
		Project project = new Project();

        // 여기에서 DTO 필드 값을 적절한 Project 필드에 할당해주세요.
        // 예: project.setTitle(projectDTO.getTitle());

        // Member 객체를 새로 생성한 Project 객체에 할당합니다.
        project.setMember(member);

        // 새로운 Project 객체를 저장하고 반환합니다.
        return projectRepository.save(project);
	}

}
