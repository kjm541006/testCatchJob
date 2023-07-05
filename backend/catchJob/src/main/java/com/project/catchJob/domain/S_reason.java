package com.project.catchJob.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member", "study"})
@Entity
public class S_reason {

	@Id @GeneratedValue
	private Long sReasonId;
	
	private String sRContents; // 모집사유
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getS_ReasonList().add(this);
	}
	
	@ManyToOne
	@JoinColumn(name = "study_id", nullable = false, updatable = false)
	private Study study;
	
	public void setStudy(Study study) {
		this.study = study;
		study.getStudyReasonList().add(this);
	}
}
