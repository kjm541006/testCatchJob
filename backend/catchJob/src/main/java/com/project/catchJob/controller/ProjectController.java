//package com.project.catchJob.controller;
//
//import com.project.catchJob.domain.member.Member;
//import com.project.catchJob.domain.project.Project;
//import com.project.catchJob.dto.project.ProjectDTO;
//import com.project.catchJob.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/")
//public class ProjectController {
//
//    private final ProjectService projectService;
//
//    @PostMapping("/buildstudy")
//    public Project addProject(@RequestBody ProjectDTO projectDTO, Authentication authentication) {
//        Member currentMember = getCurrentMember(authentication);
//        return projectService.addProject(projectDTO, currentMember);
//    }
//
//    private Member getCurrentMember(Authentication authentication) {
//        // 로그인한 사용자 정보를 얻기 위해 필요한 코드를 기록합니다.
//        // 예를 들어, Principal을 사용해서 Member 객체를 찾을 수 있습니다.
//        //
//        // 다음 라인은 예시로, 실제로는 본인의 구현에 맞게 해당 코드를 작성하십시오.
//        // UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        // return memberService.findMemberByEmail(principal.getUsername());
//
//        return null;
//    }
//}


//package com.project.catchJob.controller;
//
//import com.project.catchJob.domain.member.Member;
//import com.project.catchJob.domain.project.Project;
//import com.project.catchJob.dto.project.ProjectDTO;
//import com.project.catchJob.service.MemberService;
//import com.project.catchJob.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/")
//@CrossOrigin(origins = "http:localhost:3000")
//public class ProjectController {
//
//    private final ProjectService projectService;
//    private final MemberService memberService;
//
//    @PostMapping("/buildstudy")
//    public Project addProject(@RequestBody ProjectDTO projectDTO) {
//       return null;
//    }
//
//    
//    
//    public Member findMemberByEmail(String userEmail) {
//		return null;
//	}
//}




//
//package com.project.catchJob.controller;
//
//import com.project.catchJob.domain.member.Member;
//import com.project.catchJob.domain.project.Project;
//import com.project.catchJob.dto.project.ProjectDTO;
//import com.project.catchJob.service.MemberService;
//import com.project.catchJob.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//import javax.servlet.http.HttpSession;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/")
//public class ProjectController {
//
//    private final ProjectService projectService;
//    private final MemberService memberService;
//
//    @PostMapping("/buildstudy")
//    public Project addProject(@RequestBody ProjectDTO projectDTO, HttpSession session) {
//        String userEmail = (String) session.getAttribute("userEmail");
//
//        if (userEmail == null) {
//            throw new IllegalStateException("User not logged in");
//        }
//
//        Member member = findMemberByEmail(userEmail);
//
//        if (member == null) {
//            throw new IllegalStateException("User not found: " + userEmail);
//        }
//
//        return projectService.createProjectFromDTO(projectDTO, member);
//    }
//
//    public Member findMemberByEmail(String userEmail) {
//        return memberService.findMemberByEmail(userEmail);
//    }
//}
//
//
//package com.project.catchJob.controller;
//
//import com.project.catchJob.domain.member.Member;
//import com.project.catchJob.domain.project.Project;
//import com.project.catchJob.dto.project.ProjectDTO;
//import com.project.catchJob.service.MemberService;
//import com.project.catchJob.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//import javax.servlet.http.HttpSession;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/")
//public class ProjectController {
//
//    private final ProjectService projectService;
//    private final MemberService memberService;
//
//    @PostMapping("/buildstudy")
//    public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO, HttpSession session) {
//        String userEmail = (String) session.getAttribute("userEmail");
//
//        if (userEmail == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
//        }
//
//        Member member = findMemberByEmail(userEmail);
//
//        if (member == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + userEmail);
//        }
//
//        try {
//            Project project = projectService.createProjectFromDTO(projectDTO, member);
//            return ResponseEntity.ok().body(project);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating project: " + e.getMessage());
//        }
//    }
//
//    public Member findMemberByEmail(String userEmail) {
//        return memberService.findMemberByEmail(userEmail);
//    }
//}



package com.project.catchJob.controller;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.project.ProjectDTO;
import com.project.catchJob.service.MemberService;
import com.project.catchJob.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ProjectController {

    private final ProjectService projectService;
    private final MemberService memberService;

    @PostMapping("/buildstudy")
    public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO, @RequestParam("userEmail") String userEmail) throws Exception {

        Member member = findMemberByEmail(userEmail);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + userEmail);
        }

        try {
            Project project = projectService.createProjectFromDTO(projectDTO, member);
            return ResponseEntity.ok().body(project);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating project: " + e.getMessage());
        }
    }

    public Member findMemberByEmail(String userEmail) throws Exception {
        return memberService.findMemberByEmail(userEmail);
    }
}
