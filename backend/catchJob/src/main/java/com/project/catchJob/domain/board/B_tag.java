//package com.project.catchJob.domain.board;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@ToString(exclude = {"board","tag"})
//@Entity
//public class B_tag {
//
//	@Id @GeneratedValue(strategy = GenerationType.AUTO)
//	private Long bTagId;
//	
//	@ManyToOne
//	@JoinColumn(name = "board_id", nullable = false, updatable = false)
//	private Board board;
//	
//	public void setBoard(Board board) {
//	    if (board == null) {
//	        throw new IllegalArgumentException("board must not be null");
//	    }
//	    this.board = board;
//	}
//	
//	@ManyToOne
//    @JoinColumn(name = "tag_id", nullable = false, updatable = false)
//    private Tag tag;
//	
//	public void setTag(Tag tag) {
//	    if (tag == null) {
//	        throw new IllegalArgumentException("tag must not be null");
//	    }
//	    this.tag = tag;
//	}
//
////	@OneToMany(mappedBy = "b_tag", cascade = CascadeType.ALL)
////	private List<Tag> tagList = new ArrayList<>();
//
//}
