package com.project.catchJob.dto.project;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member", "projectCommentsList", "projectLikeList", "projectReasonList"})
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long projectId;
    private String type;
    private String title;
    private String field;
    private String loc;
    private String term;
    private String detail;
    private int webDesigner;
    private int webPublisher;
    private int frontend;
    private int backend;
    private int PM;
    private int others;
    private String platform;
    private int pCnt;
    private int pLike;
    private Date pDate;
    private Long memberId;
}
	
	// Assuming you have corresponding DTO classes for P_comments, P_like, P_reason, and P_member
//	private List<P_commentsDTO> projectCommentsList;
//	
//	private List<P_likeDTO> projectLikeList;
//	
//	private List<P_reasonDTO> projectReasonList;
//	
//	private List<P_memberDTO> projectMemberList;	

