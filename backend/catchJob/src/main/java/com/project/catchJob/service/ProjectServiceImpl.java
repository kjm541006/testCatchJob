package com.project.catchJob.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.project.catchJob.domain.board.B_comments;
import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.P_comments;
import com.project.catchJob.domain.project.P_like;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.dto.board.B_commentsDTO;
import com.project.catchJob.dto.board.CommentResponse;
import com.project.catchJob.dto.member.BoardMemberDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.dto.member.MemberInfoDTO;
import com.project.catchJob.dto.project.P_commentsDTO;
import com.project.catchJob.dto.project.ProjectDTO;
import com.project.catchJob.exception.UnauthorizedException;
import com.project.catchJob.repository.member.MemberRepository;
import com.project.catchJob.repository.project.PLikeRepository;
import com.project.catchJob.repository.project.P_commentsRepository;
import com.project.catchJob.repository.project.ProjectRepository;
import com.project.catchJob.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired private final ProjectRepository projectRepository;
	@Autowired private MemberRepository memberRepository;
	@Autowired private JwtUtils jwtUtils;
	@Autowired private CommonService commonService;
	@Autowired private P_commentsRepository pCommRepo;
	@Autowired private PLikeRepository pLikeRepo;
	
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
	 public ProjectDTO getProjectByProjectId(Long projectId) {
	 
		Project project = projectRepository.findById(projectId)
	            .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다."));
		
		Member writer = project.getMember();		
		
		if(writer != null) {
	
			String fileUrl = "http://43.202.98.45:8089/upload/";
	
			MemberInfoDTO memberDTO = MemberInfoDTO.builder()
					.email(writer.getEmail())
					.name(writer.getName())
					.job(writer.getJob())
					.hasCareer(writer.getHasCareer())
					.mOriginalFileName(fileUrl + writer.getMProfile().getMStoredFileName())
					.build();
			
			List<P_commentsDTO> comments = project.getProjectCommentsList() != null ? project.getProjectCommentsList().stream()
					.map(comment -> P_commentsDTO.builder()
							.commentId(comment.getPComId())
							.commentContent(comment.getPComContent())
							.commentDate(comment.getPComDate())
							.memberName(comment.getMember().getName())
							.memberEmail(comment.getMember().getEmail())
							.memberProfile(fileUrl + comment.getMember().getMProfile().getMStoredFileName())
							.build())
					.collect(Collectors.toList()) : new ArrayList<>();
			
			ProjectDTO projectDTO = new ProjectDTO();
			projectDTO.setPCnt(project.getPCnt());
			projectDTO.setPLike(project.getPLike());
			projectDTO.setProjectId(project.getProjectId());
	        projectDTO.setTitle(project.getTitle());
	        projectDTO.setField(project.getField());
	        projectDTO.setTerm(project.getTerm());
	        projectDTO.setPlatforms(project.getPlatforms() != null ? new ArrayList<>(project.getPlatforms()) : new ArrayList<>());
	        projectDTO.setType(project.getType());
	        projectDTO.setLoc(project.getLoc());
	        projectDTO.setCrew(project.getCrew());
	        projectDTO.setDetail(project.getDetail());
	        projectDTO.setEmail(memberDTO.getEmail());
	        projectDTO.setMember(memberDTO);
	        projectDTO.setComments(comments);
			
	        return projectDTO;
		}
		return null;
	 }
	 
	//======================== 댓글 ========================
	 
	 // 댓글 등록
	 @Override
	 public CommentResponse createComment(P_commentsDTO commentDTO, Long projectId, String jwtToken) {
		    
	    	Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
		    		.orElseThrow(UnauthorizedException::new);
		    
	    	Member member = memberRepository.findByEmail(optAuthenticatedMember.getEmail());
	    	
	    	Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("게시글이 없음"));
	    	
	    	P_comments comments = P_comments.builder()
	    			.pComContent(commentDTO.getCommentContent())
	    			.member(member)
	    			.project(project)
	    			.build();
	    	P_comments saveComm = pCommRepo.save(comments);
	    	pCommRepo.flush();
	    	return new CommentResponse(saveComm.getPComDate());
	    }
	 
	 // 댓글 수정
	 @Override
	 public CommentResponse editComment(P_commentsDTO commentDTO, Long commentId, String jwtToken) {
		 
		 Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
				 .orElseThrow(UnauthorizedException::new);
		 
		 P_comments comment = pCommRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글이 없음"));
		 
		 if(!optAuthenticatedMember.getEmail().equals(comment.getMember().getEmail())) {
			 throw new UnauthorizedException();
		 }
		 comment.setPComContent(commentDTO.getCommentContent());
		 comment.setPComDate(LocalDateTime.now());
		 P_comments saveComm = pCommRepo.save(comment);
		 pCommRepo.flush();
		 return new CommentResponse(saveComm.getPComDate());
	 }

	// 댓글 삭제
	@Override
	public void deleteComment(Long commentId, String jwtToken) {
		
		Member optAuthenticatedMember = commonService.getAuthenticatedMember(jwtToken)
				 .orElseThrow(UnauthorizedException::new);
		
		P_comments comment = pCommRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글이 없음"));
		
		if(!optAuthenticatedMember.getEmail().equals(comment.getMember().getEmail())) {
			throw new UnauthorizedException();
		 }
		pCommRepo.deleteById(commentId);
		 
	}
	
	//======================== 좋아요 ========================
	
	// 좋아요 확인
	@Override
	public boolean isUserLiked(String email, Long projectId) {
		Member member = memberRepository.findOptionalByEmail(email).orElse(null);
		if(member == null) {
			return false;
		}
		
		Project project = projectRepository.findById(projectId).orElse(null);
		if(project == null) {
			return false;
		}
		
		Optional<P_like> like = pLikeRepo.findByMemberAndProject(member, project);
		return like.isPresent();
	}

	// 좋아요 추가
	@Override
	public void insert(String email, Long projectId) {
		
	}

	// 좋아요 취소
	@Override
	public void delete(String email, Long projectId) {
		
	}

	// 좋아요 수 업데이트
	@Override
	public Project updateLike(Long projectId, boolean b) {
		return null;
	}
	
	//======================== 조회수 ========================
	
	// 조회수 업데이트
	@Override
	public int updateCnt(Long projectId) throws NotFoundException {
		projectRepository.updateCnt(projectId);
		Project project = projectRepository.findById(projectId).orElseThrow(NotFoundException::new);
		return project.getPCnt();
	}
}