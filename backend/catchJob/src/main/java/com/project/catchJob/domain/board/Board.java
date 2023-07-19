package com.project.catchJob.domain.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.project.catchJob.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString(exclude = {"member","boardCommentsList", "boardLikeList", "boardTagList"})
@Entity
public class Board {
	
	@Id @GeneratedValue @Column(name = "board_id")
	private Long boardId;
	
	private String bTitle;
	
	@Lob
	private String bContents; // editor사용(대용량)
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private int bCnt; // 조회수
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private int bLike; // 좋아요갯수
	
	private String bFileName; // 파일명
	
	@Transient
	private MultipartFile bUploadFile; // 실제경로
	
	private String bCoverFileName; // 커버(썸네일) 파일명
	
	@Transient
	private MultipartFile bCoverUploadFile; // 커버(썸네일) 실제경로
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date bDate; // 작성날짜
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getBoardList().add(this);
	}
	
	@OneToMany(mappedBy = "board")
	private List<B_comments> boardCommentsList = new ArrayList<>();
	
	@OneToMany(mappedBy = "board")
	private List<B_like> boardLikeList = new ArrayList<>();
	
	@OneToMany(mappedBy = "board")
	private List<B_tag> boardTagList = new ArrayList<>();

}
