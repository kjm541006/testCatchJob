package com.project.catchJob.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "member")
@Entity
public class Board {
	
	@Id @GeneratedValue
	private Long boardId;
	
	private String bTitle;
	
	@Lob
	private String bContents;
	
	private int bCnt; // 조회수
	
	private int bLike; // 좋아요갯수
	
	
	

}
