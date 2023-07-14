package com.project.catchJob.domain.community;

import java.util.Date;


import javax.persistence.*;

import com.project.catchJob.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member","community"})
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String user; 
	
	@Column(columnDefinition ="TEXT")
	private Date content; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Community community;
	private CommunityPost post;
	
	
	
}