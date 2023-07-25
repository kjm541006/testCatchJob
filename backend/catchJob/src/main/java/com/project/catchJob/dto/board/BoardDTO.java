package com.project.catchJob.dto.board;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.project.catchJob.domain.board.B_tag;
import com.project.catchJob.domain.board.Board;
//import com.project.catchJob.domain.board.Tag;
import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.member.BoardMemberDTO;
import com.project.catchJob.dto.member.MemberDTO;
import com.project.catchJob.repository.board.B_likeRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

	private Long boardId;
	
	@JsonProperty("bTitle")
	private String bTitle;
	
	@JsonProperty("bContents")
	private String bContents;
	
	@JsonProperty("bCnt")
	private int bCnt;
	
	@JsonProperty("bLike")
	private int bLike;
	
	@JsonProperty("isLike")
	private boolean isLike; // 내가 좋아요했는지 알 수 있는 여부
	
	@JsonProperty("bComment")
	private int bComment; // 댓글갯수
	
	@JsonProperty("bFileName")
	private String bFileName;
	
	@JsonProperty("bFileUrl")
	private String bFileUrl;
	
	@JsonProperty("bCoverFileName")
	private String bCoverFileName;
	
	@JsonProperty("bCoverFileUrl")
	private String bCoverFileUrl;
	
	@JsonProperty("bDate")
	private Date bDate;
	
	private List<String> tags;
	
	private BoardMemberDTO member;
	// private MemberDTO member;
	//private List<TagDTO> tags;
	private List<B_commentsDTO> comments;
	
	// board에서 BoardDTO로 변환하는 메서드
	public static BoardDTO toDTO(Board board, Member member, B_likeRepository bLikeRepo, String filePath) {
		
		// 필요한 사용자 정보를 memberDTO에 저장
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setEmail(member.getEmail());
//		memberDTO.setName(member.getName());
		//memberDTO.setMOriginalFileName(member.getMProfile().getMOriginalFileName());

		BoardMemberDTO memberDTO = new BoardMemberDTO();
		Member writer = board.getMember();
		if(writer != null) {
			memberDTO.setEmail(board.getMember().getEmail());
			memberDTO.setName(board.getMember().getName());
			//memberDTO.setMOriginalFileName(board.getMember().getMProfile().getMOriginalFileName());
		}
		
		// 내가 좋아요했는지 여부 확인
		boolean isLike = bLikeRepo.findByMemberAndBoard(member, board).isPresent();
		
//		List<TagDTO> tagDTOList = board.getBoardTagList().stream()
//			    .map(bTag -> {
//			        Tag tag = bTag.getTag();
//			        return TagDTO.builder()
//			            .tagId(tag.getTagId())
//			            .tagName(tag.getTagName())
//			            .build();
//			    })
//			    .collect(Collectors.toList());
		
		List<String> tagList = board.getTags();
		String[] tags = tagList.toArray(new String[0]);
		
		int bComment = board.getBoardCommentsList().size(); // 게시글에 작성된 댓글 수를 구함
		
		List<B_commentsDTO> commentDTOList = board.getBoardCommentsList().stream()
				.map(comment -> B_commentsDTO.builder()
						.commentContent(comment.getBComContent())
						.commentDate(comment.getBComDate())
						.memberName(comment.getMember().getName())
						.memberEmail(comment.getMember().getEmail())
						.build())
				.collect(Collectors.toList());
				
		//String bFileUrl = "/upload/" + board.getBFileName();
		String bFileUrl = filePath + board.getBFileName();
		String bCoverFileUrl = filePath + board.getBCoverFileName();
		
		return BoardDTO.builder()
				.boardId(board.getBoardId())
				.bTitle(board.getBTitle())
				.bContents(board.getBContents())
				.bCnt(board.getBCnt())
				.bLike(board.getBLike())
				.isLike(isLike) // isLike 설정
				.bComment(bComment)
				.bFileName(board.getBFileName())
				.bFileUrl(bFileUrl)
				.bCoverFileName(board.getBCoverFileName())
				.bCoverFileUrl(bCoverFileUrl)
				.bDate(board.getBDate())
				.member(memberDTO) // 멤버 정보 설정
				.tags(board.getTags()) // 태그
				.comments(commentDTOList) // 댓글
				.build();
	}

	// 로그인하지 않은 사용자
	public static BoardDTO toDTOWithoutMember(Board board, String filePath) {
		// 필요한 사용자 정보를 memberDTO에 저장
		BoardMemberDTO memberDTO = new BoardMemberDTO();
		Member member = board.getMember();
		if(member != null) {
			memberDTO.setEmail(board.getMember().getEmail());
			memberDTO.setName(board.getMember().getName());
			//memberDTO.setMOriginalFileName(board.getMember().getMProfile().getMOriginalFileName());
		}

		int bComment = board.getBoardCommentsList().size(); // 댓글 수 계산

//		List<B_commentsDTO> commentDTOList = board.getBoardCommentsList().stream()
//				.map(comment -> B_commentsDTO.builder()
//						.commentContent(comment.getBComContent())
//						.commentDate(comment.getBComDate())
//						.memberName(comment.getMember().getName())
//						.memberEmail(comment.getMember().getEmail())
//						.build())
//				.collect(Collectors.toList());
//
//		List<String> tagList = board.getTags();
//		String[] tags = tagList.toArray(new String[0]);

		List<String> tags = board.getTags() != null ? board.getTags() : new ArrayList<>();
		List<B_commentsDTO> comments = board.getBoardCommentsList() != null ? board.getBoardCommentsList().stream()
		        .map(comment -> B_commentsDTO.builder()
		            .commentContent(comment.getBComContent())
		            .commentDate(comment.getBComDate())
		            .memberName(comment.getMember().getName())
		            .memberEmail(comment.getMember().getEmail())
		            .build())
		        .collect(Collectors.toList()) : new ArrayList<>();
		String bFileUrl = "";
	    String bCoverFileUrl = "";

	    if (board.getBFileName() != null) {
	        bFileUrl = filePath + board.getBFileName();
	    }

	    if (board.getBCoverFileName() != null) {
	        bCoverFileUrl = filePath + board.getBCoverFileName();
	    }

		return BoardDTO.builder()
				.boardId(board.getBoardId())
				.bTitle(board.getBTitle())
				.bContents(board.getBContents())
				.bCnt(board.getBCnt())
				.bLike(board.getBLike())
				.isLike(false) // 로그인하지 않은 사용자는 항상 false
				.bComment(bComment) // 댓글 수 계산
				.bFileName(board.getBFileName())
				.bFileUrl(bFileUrl)
				.bCoverFileName(board.getBCoverFileName())
				.bCoverFileUrl(bCoverFileUrl)
				.bDate(board.getBDate())
				.member(memberDTO) // 멤버 정보 설정
				.tags(board.getTags()) // 태그
				.comments(comments) // 댓글
				.build();
	}
	
}

