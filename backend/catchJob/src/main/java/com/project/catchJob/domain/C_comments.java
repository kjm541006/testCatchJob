package com.project.catchJob.domain;

import java.util.Date;

import javax.persistence.Column;
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
	@ToString(exclude = {"member","community"})
	@Entity
	public class C_comments {

		@Id @GeneratedValue
		private Long cComId; // Ä¿¹Â´ÏÆ¼´ñ±Û ¾ÆÀÌµð
		
		private String cComContent; // ´ñ±Û ³»¿ë
		
		@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
		private Date cComDate; // ´ñ±Û ÀÛ¼º³¯Â¥
		
		@ManyToOne
		@JoinColumn(name = "member_id", nullable = false, updatable = false)
		private Member member;
		
		public void setMember(Member member) {
			this.member = member;
			member.getC_CommentsList().add(this);
		}
		
		@ManyToOne
		@JoinColumn(name = "community_id", nullable = false, updatable = false)
		private Community community;
		
		public void setCommunity(Community community) {
			this.community = community;
			community.getComConList().add(this);
		}
		
	}