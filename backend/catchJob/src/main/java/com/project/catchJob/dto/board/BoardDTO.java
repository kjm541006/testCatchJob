package com.project.catchJob.dto.board;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.catchJob.domain.board.B_tag;
import com.project.catchJob.domain.board.Board;
import com.project.catchJob.domain.board.Tag;
import com.project.catchJob.domain.member.Member;
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

	//private Long boardId;
	
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
	
	private MemberDTO member;
	private List<TagDTO> tags;
	private List<B_commentsDTO> comments;
	
	// board에서 BoardDTO로 변환하는 메서드
	public static BoardDTO toDTO(Board board, Member member, B_likeRepository bLikeRepo, String filePath, List<TagDTO> tagDTOList) {
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmail(member.getEmail());
		memberDTO.setName(member.getName());
		memberDTO.setMOriginalFileName(member.getMProfile().getMOriginalFileName());

		// 필요한 사용자 정보를 memberDTO에 저장
		
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
		
		List<TagDTO> tagList = board.getBoardTagList().stream()
			    .map(bTag -> {
			        Tag tag = bTag.getTag();
			        return TagDTO.builder()
			            //.tagId(tag.getTagId())
			            .tagName(tag.getTagName())
			            .build();
			    })
			    .collect(Collectors.toList());
		
		int bComment = board.getBoardCommentsList().size(); // 게시글에 작성된 댓글 수를 구함
		
		List<B_commentsDTO> commentDTOList = board.getBoardCommentsList().stream()
				.map(comment -> B_commentsDTO.builder()
						.commentId(comment.getBComId())
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
				//.boardId(board.getBoardId())
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
				.tags(tagList) // 태그
				.comments(commentDTOList) // 댓글
				.build();
	}

	
}

