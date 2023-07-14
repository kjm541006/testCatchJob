package com.project.catchJob.domain.community;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommunityPost {
	
	private Long id;
	
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private String category;

	private List<Comment> comments = new ArrayList<>();
	
}
