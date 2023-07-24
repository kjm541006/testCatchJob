package com.project.catchJob.domain.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor // 기본 생성자 추가
@Data
@ToString(exclude = "b_tagList")
@Entity
public class Tag {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long tagId;
	
	private String tagName;
	
//	@ManyToOne
//	@JoinColumn(name = "b_tag_id", nullable = false, updatable = false)
//	private B_tag b_tag;
//	
//	public void setBTag(B_tag b_tag) {
//		this.b_tag = b_tag;
//		b_tag.getTagList().add(this);
//	}

	@OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<B_tag> b_tagList = new ArrayList<>();

}
