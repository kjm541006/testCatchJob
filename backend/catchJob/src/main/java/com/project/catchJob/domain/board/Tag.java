package com.project.catchJob.domain.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString(exclude = "b_tagList")
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

	@OneToMany(mappedBy = "tag")
    private List<B_tag> b_tagList = new ArrayList<>();

}
