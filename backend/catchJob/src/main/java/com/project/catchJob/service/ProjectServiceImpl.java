package com.project.catchJob.service;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.project.ProjectDTO;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.repository.project.ProjectRepository;
import com.project.catchJob.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private final ProjectRepository projectRepository;
	
	@Autowired
    private MemberRepository memberRepository;
	
	@Autowired
    private JwtUtils jwtUtils;
	

//	public Project addProject(ProjectDTO projectDTO) {
//		Project project = new Project();
//
//
//	    projectRepository.save(project);
//	    return project;
//	}
	
	@Override
	public Project addProject(ProjectDTO projectDTO, String userEmail) {
		Member member = memberRepository.findByEmail(projectDTO.getEmail());
		
		Project project = new Project();
        project.setTitle(projectDTO.getTitle());
        project.setField(projectDTO.getField());
        project.setTerm(projectDTO.getTerm());
//        project.setPlatform(projectDTO.getPlatform());
//        project.setPlatforms(new HashSet<>(projectDTO.getPlatforms()));
        project.setPlatforms(projectDTO.getPlatforms() != null ? new HashSet<>(projectDTO.getPlatforms()) : new HashSet<>());

        project.setLoc(projectDTO.getLoc());
        project.setCrew(projectDTO.getCrew());
        project.setDetail(projectDTO.getDetail());
        project.setMember(member);
		
        return projectRepository.save(project);
	}

}
