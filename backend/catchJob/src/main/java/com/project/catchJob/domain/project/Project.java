package com.project.catchJob.domain.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import com.project.catchJob.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member", "projectCommentsList", "projectLikeList", "projectReasonList"})
@Entity
public class Project {

	@Id @GeneratedValue @Column(name = "project_id")
	private Long projectId;
	
	private String bType; // 분야
	
	private String title;
	
	private String field;
	
	private String loc; // 지역
	
	private String term; // 모집기간
	
	private String detail;
	
//	private int webDesigner; // 웹디자인 인원
//	
//    private int webPublisher; // 웹퍼블리셔 인원
//    
//    private int frontend; // 프론트엔드 인원
//    
//    private int backend; // 백엔드 인원
//    
//    private int PM; // 프로젝트 매니저 인원
//    
//    private int others; // 기타 인원
	
	@ElementCollection
    @CollectionTable(name = "crew_counts", joinColumns = @JoinColumn(name = "project_id"))
    @MapKeyColumn(name = "role")
    @Column(name = "count")
    private Map<String, Integer> crew;
	
	// private String platform; // 출시플랫폼
	
	@ElementCollection(fetch = FetchType.EAGER)
	  @CollectionTable(name = "platforms", joinColumns = @JoinColumn(name = "project_id"))
	  @Column(name = "platform")
	  private Set<String> platforms; // 출시플랫폼을 Set으로 변경

	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private int pCnt; // 조회수
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private int pLike; // 좋아요갯수
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date pDate; // 작성날짜
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getProjectList().add(this);
	}

	@OneToMany(mappedBy = "project")
	private List<P_comments> projectCommentsList = new ArrayList<>();
	
	@OneToMany(mappedBy = "project")
	private List<P_like> projectLikeList = new ArrayList<>();	
	
	@OneToMany(mappedBy = "project")
	private List<P_reason> projectReasonList = new ArrayList<>();	
	
	@OneToMany(mappedBy = "project")
	private List<P_member> projectMemberList = new ArrayList<>();	
}