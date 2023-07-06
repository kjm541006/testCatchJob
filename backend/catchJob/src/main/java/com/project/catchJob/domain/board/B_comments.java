package com.project.catchJob.domain.board;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.project.catchJob.domain.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member", "board"})
@Entity
public class B_comments {

	@Id @GeneratedValue
	private Long bComId; // 보드댓글 아이디
	
	private String bComContent; // 댓글 내용
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date bComDate; // 댓글 작성날짜
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getB_CommentsList().add(this);
	}
	
	@ManyToOne
	@JoinColumn(name = "board_id", nullable = false, updatable = false)
	private Board board;
	
	public void setBoard(Board board) {
		this.board = board;
		board.getBoardCommentsList().add(this);
	}
}
