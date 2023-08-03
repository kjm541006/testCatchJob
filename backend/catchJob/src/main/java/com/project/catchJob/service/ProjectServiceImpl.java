package com.project.catchJob.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.member.MemberDTO;
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
	
	@Override
	public Project addProject(ProjectDTO projectDTO, String userEmail) {
		Member member = memberRepository.findByEmail(projectDTO.getEmail());
		
		Project project = new Project();
        project.setTitle(projectDTO.getTitle());
        project.setField(projectDTO.getField());
        project.setTerm(projectDTO.getTerm());
        project.setPlatforms(projectDTO.getPlatforms() != null ? new ArrayList<>(projectDTO.getPlatforms()) : new ArrayList<>());
        project.setType(projectDTO.getType());
        project.setLoc(projectDTO.getLoc());
        project.setCrew(projectDTO.getCrew());
        project.setDetail(projectDTO.getDetail());
        project.setMember(member);
		
        return projectRepository.save(project);
	}
	
	 public List<Project> getAllProjects() {
	        return projectRepository.findAll();
	    }
	 
//	 public MemberDTO toMemberDTO(Member member) {
//		    MemberDTO memberDTO = new MemberDTO();
//		    memberDTO.setMemberId(member.getMemberId());
//		    memberDTO.setName(member.getName());
//		    memberDTO.setEmail(member.getEmail());
//		    // 기타 필요한 필드 설정
//
//		    return memberDTO;
//		}

	 
	 
	 @Override
	    public Project getProjectByProjectId(Long projectId) {
	        return projectRepository.findById(projectId)
	            .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다."));
	    }
	 
	 




}