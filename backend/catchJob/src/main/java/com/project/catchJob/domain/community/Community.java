package com.project.catchJob.domain.community;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.project.catchJob.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"member","comments"})
@Entity
@Table(name ="community")
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_id")
	private Long cId;
		
	private String cTitle; // 제목
	
	private String ccategory; // 분류
	
	private String cContents; // 내용
	
	private String instUser; // 작성유저
	
	private String instDate; // 작성날짜
	
	private String profileImg; // 프로필이미지
	
	
	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
	
	public Community() {
		
	}
	public Community(String title, String category, String content, String instUser, String instDate, String profileImg) {
		this.cTitle= title;
		this.ccategory = category;
		this.cContents = content;
		this.instUser = instUser;
		this.instDate = instDate;
		this.profileImg = profileImg;
		
	}
	public void addComment(Comment comment) {
        comments.add(comment);
        comment.setCommunity(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setCommunity(null);
    }
}