//package com.project.catchJob.controller;
//
//import com.project.catchJob.domain.member.Member;
//import com.project.catchJob.domain.project.Project;
//import com.project.catchJob.dto.project.ProjectDTO;
//import com.project.catchJob.repository.member.MemberRepository;
//import com.project.catchJob.security.JwtUtils;
//import com.project.catchJob.service.MemberService;
//import com.project.catchJob.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/")
//public class ProjectController {
//
//	@Autowired
//    private final ProjectService projectService;
//    private final MemberService memberService;
//    
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @PostMapping("/buildproject")
//    public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO, HttpServletRequest request) {
//    	String userEmail = jwtUtils.getEmailFromRequest(request);
//    	Project savedProject = projectService.addProject(projectDTO, userEmail);
//    	return ResponseEntity.ok(savedProject.getProjectId());
//    }
//
////    public Member findMemberByEmail(String userEmail) {
////        return memberService.findMemberByEmail(userEmail);
////    }
//}
