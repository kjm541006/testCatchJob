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
@ToString(exclude = "b_tag")
@Entity
public class Tag {

	@Id @GeneratedValue
	private Long tagId;
	
	private String tagName;
	
	@ManyToOne
	@JoinColumn(name = "b_tag_id", nullable = false, updatable = false)
	private B_tag b_tag;
	
	public void setBTag(B_tag b_tag) {
		this.b_tag = b_tag;
		b_tag.getTagList().add(this);
	}
}
