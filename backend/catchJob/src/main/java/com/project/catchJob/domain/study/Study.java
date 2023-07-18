package com.project.catchJob.domain.study;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.project.catchJob.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member", "studyCommentsList", "studyLikeList", "studyReasonList"})
@Entity
public class Study {

	@Id @GeneratedValue @Column(name = "study_id")
	private Long studyId;
	
	private String sType; // 분야
	
	private String sTitle;
	
	private String sContents;
	
	private String sLoc; // 지역
	
	private String sPeriod; // 모집기간
	
	private int sMemCnt; // 모집인원
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private int sCnt; // 조회수
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private int sLike; // 좋아요갯수	
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date sDate; // 작성날짜
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getStudyList().add(this);
	}

	@OneToMany(mappedBy = "study")
	private List<S_comments> studyCommentsList = new ArrayList<>();
	
	@OneToMany(mappedBy = "study")
	private List<S_like> studyLikeList = new ArrayList<>();	
	
	@OneToMany(mappedBy = "study")
	private List<S_reason> studyReasonList = new ArrayList<>();	
}
