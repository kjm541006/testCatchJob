package com.project.catchJob.dto.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.domain.project.P_member;
import com.project.catchJob.dto.board.B_commentsDTO;
import com.project.catchJob.dto.member.BoardMemberDTO;
import com.project.catchJob.dto.member.MemberInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString(exclude = {"member", "comments", "applicants"})
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
    private List<String> platforms;
    private int pCnt;
    private int pLike;
    private boolean isLike;
    private Map<String, Integer> crew;
    private String email;
    
	private MemberInfoDTO member;
	private List<P_commentsDTO> comments;
	private List<P_memberDTO> applicants;
}
