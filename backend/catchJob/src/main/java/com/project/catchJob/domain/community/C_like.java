package com.project.catchJob.domain.community;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.Project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member", "community"})
@Entity
public class C_like {

	@Id @GeneratedValue
	private Long cLikId; // 커뮤니티좋아요 아이디
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getC_LikeList().add(this);
	}
	
	@ManyToOne
	@JoinColumn(name = "community_id", nullable = false, updatable = false)
	private Community community;
	
	public void setCommunity(Community community) {
		this.community = community;
		community.getCommunityLikeList().add(this);
	}
}
