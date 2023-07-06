package com.project.catchJob.domain.board;

import java.util.ArrayList;
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
@ToString(exclude = {"board","tagList"})
@Entity
public class B_tag {

	@Id @GeneratedValue @Column(name = "b_tag_id")
	private Long bTagId;
	
	@ManyToOne
	@JoinColumn(name = "board_id", nullable = false, updatable = false)
	private Board board;
	
	public void setBoard(Board board) {
		this.board = board;
		board.getBoardTagList().add(this);
	}
	
	@OneToMany(mappedBy = "b_tag")
	private List<Tag> tagList = new ArrayList<>();
}
