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
package com.project.catchJob.controller;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.board.B_commentsDTO;
import com.project.catchJob.dto.board.CommentResponse;
import com.project.catchJob.dto.project.P_commentsDTO;
import com.project.catchJob.dto.project.ProjectDTO;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.security.JwtUtils;
import com.project.catchJob.service.MemberService;
import com.project.catchJob.service.ProjectService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ProjectController {

	@Autowired
    private final ProjectService projectService;
    private final MemberService memberService;
    
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/buildproject")
    public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO, HttpServletRequest request) {
    	System.out.println("------------------------------------------");
    	System.out.println(request);
    	String userEmail = jwtUtils.getEmailFromRequest(request);
    	System.out.println(userEmail);
    	Project savedProject = projectService.addProject(projectDTO, userEmail);
    	return ResponseEntity.ok(savedProject.getProjectId());
    }
    
    @GetMapping("/project")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
//    @GetMapping("/studyDetail/{id}")
//    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
//        Project project = projectService.getProjectById(id);
//        return ResponseEntity.ok(project);
//    }
    
//    
//    @GetMapping("/studyDetail")
//    public ResponseEntity<Project> getProjectByProjectId(@RequestParam("projectId") Long projectId) {
//    	System.out.println(projectId);
//        Project project = projectService.getProjectByProjectId(projectId);
//        return ResponseEntity.ok(project);
//    }
    
    // 글 조회
    @GetMapping("/studyDetail/{id}")
    public ResponseEntity<?> getProjectByProjectId(@PathVariable("id") Long projectId) throws NotFoundException {
    	System.out.println(projectId);
    	projectService.updateCnt(projectId);
        ProjectDTO project = projectService.getProjectByProjectId(projectId);
        
        return ResponseEntity.ok(project);
    }
    
  //======================== 댓글 ========================
    
    // 댓글 등록
    @PostMapping("/studyDetail/comment/{id}")
    public ResponseEntity<?> registerComment(
    		@RequestBody P_commentsDTO commentDTO,
			@PathVariable("id") Long projectId,
			@RequestHeader("Authorization") String jwtToken) {
		
		CommentResponse commentRes = projectService.createComment(commentDTO, projectId, jwtToken);
		
		return ResponseEntity.ok(commentRes);
	}
    
    // 댓글 수정
    @PutMapping("/studyDetail/comment/edit/{id}")
    public ResponseEntity<?> editComment(
    		@RequestBody P_commentsDTO commentDTO,
    		@PathVariable("id") Long commentId,
    		@RequestHeader("Authorization") String jwtToken) {
    	
    	CommentResponse commentRes = projectService.editComment(commentDTO, commentId, jwtToken);
    	
    	return ResponseEntity.ok(commentRes);
    }
    
    // 댓글 삭제
    @DeleteMapping("/studyDetail/comment/delete/{id}")
    public ResponseEntity<?> deleteComment(
    		@PathVariable("id") Long commentId,
    		@RequestHeader("Authorization") String jwtToken) {
    	
    	projectService.deleteComment(commentId, jwtToken);
    	
    	return ResponseEntity.ok().build();
    }
    
    //======================== 좋아요 ========================
    

}