package com.project.catchJob.dto;

public class CommunityCommentDTO {
	
	private Long id;
	private String content;
	
	public CommunityCommentDTO() {
		
	}
	
	public CommunityCommentDTO(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	// Getters and setters
	  public Long getId() {
	    return id;
	  }

	  public void setId(Long id) {
	    this.id = id;
	  }

	  public String getContent() {
	    return content;
	  }

	  public void setContent(String content) {
	    this.content = content;
	  }
	}
