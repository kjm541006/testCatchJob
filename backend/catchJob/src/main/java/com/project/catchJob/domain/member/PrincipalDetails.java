package com.project.catchJob.domain.member;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PrincipalDetails implements UserDetails, OAuth2User { 
	// 일반회원로그인과 소셜로그인 방법 구분없이 한 객체로 관리하기위해
	// PrincipalDetails에 OAuth2User도 implements
	
	private Member member;
	private Map<String, Object> attributes;
	
	// UserDetails : 일반회원 로그인
	public PrincipalDetails(Member member) {
		this.member = member;
	}
	
	// OAuth2User : 소셜로그인
	public PrincipalDetails(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}

	
	// OAuth2User
	@Override
	public String getName() {
		String sub = attributes.get("sub").toString();
		return sub;
	}

	// UserDetails 권한 부분은 따로 설정하지 않아서 주석처리
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	// UserDetails - 계정활성화여부(t-활성화/f-비활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
