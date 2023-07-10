package com.project.catchJob.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.project.catchJob.domain.board.B_comments;
import com.project.catchJob.domain.board.B_like;
import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.community.C_comments;
import com.project.catchJob.domain.community.Community;
import com.project.catchJob.domain.project.P_comments;
import com.project.catchJob.domain.project.P_like;
import com.project.catchJob.domain.project.P_reason;
import com.project.catchJob.domain.project.Project;
import com.project.catchJob.domain.study.S_comments;
import com.project.catchJob.domain.study.S_like;
import com.project.catchJob.domain.study.S_reason;
import com.project.catchJob.domain.study.Study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {
		"communityList", "c_CommentsList", 
		"boardList", "b_CommentsList", "b_LikeList",
		"studyList", "s_CommentsList", "s_LikeList", "s_ReasonList",
		"projectList", "p_CommentsList", "p_LikeList", "p_ReasonList"})
@Entity
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long memberId; // email 넘 길어서 식별하려고 만든 아이디
	
	private String name;
	
	private String email;
	
	private String pwd;
	
	@ElementCollection
	private List<String> job; // 직무
	
	private String hasCareer; // 경력여부

	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Community> communityList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<C_comments> c_CommentsList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Board> boardList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<B_comments> b_CommentsList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<B_like> b_LikeList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Study> studyList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<S_comments> s_CommentsList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<S_like> s_LikeList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<S_reason> s_ReasonList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Project> projectList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<P_comments> p_CommentsList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<P_like> p_LikeList = new ArrayList<>();
	
	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<P_reason> p_ReasonList = new ArrayList<>();

}
