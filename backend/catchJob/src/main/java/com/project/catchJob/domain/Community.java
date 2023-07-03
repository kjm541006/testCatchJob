package com.project.catchJob.domain;

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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member","comConList"})
@Entity
public class Community {

	@Id @GeneratedValue @Column(name = "community_id")
	private Long communityId;
	
	private String cType; // 분류
	
	private String cTitle; // 제목
	
	private String cContents; // 내용
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date cDate; // 작성날짜
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long cLike; // 좋아요
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getCommunityList().add(this);
	}

	@OneToMany(mappedBy = "community")
	private List<C_comments> comConList= new ArrayList<>();
	
}