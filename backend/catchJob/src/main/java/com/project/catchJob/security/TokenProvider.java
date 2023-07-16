package com.project.catchJob.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.catchJob.domain.member.Member;
import com.project.catchJob.dto.member.MemberDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	
	// 강사님 코드 lec02_todo_auth 참고
	// https://velog.io/@kjy0302014/Spring-Security-%EC%97%86%EC%9D%B4-Spring-JWT-%EC%9D%B8%EC%A6%9D-%EC%9D%B8%EA%B0%80-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0 참고
	
	private static final String SECRET_KEY = "catchJobSecretKeycatchJobSecretKeycatchJobSecretKeycatchJobSecretKeycatchJobSecretKey";
	
	// 로그인 시 토큰 생성
	public String createToken(Member member) {
		// 기한 지금으로부터 1일로 설정
		Date expireDate = Date.from(
			Instant.now()
				.plus(1, ChronoUnit.DAYS));
		
		return Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
				.setSubject(member.getEmail()) // sub 토큰제목(토큰에서 사용자에 대한 식별 값)
				.setIssuer("Token by catchJob") // iss 토큰발급자
				.setIssuedAt(new Date()) // iat 토큰발급시간
				.setExpiration(expireDate) // exp 토큰만료시간
				.claim("member_id", member.getMemberId()) // member_id 저장
				.compact();
	}

	// 토큰 검증
	public String validateToken(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
							.build().parseClaimsJws(token)
							.getBody();
		log.info("claims.getSubject = " + claims.getSubject());
		
		return claims.getSubject();
	}
	
	// 로그아웃 시 토큰 무효화
	public ResponseEntity<?> deleteToken(@RequestBody MemberDTO memberDTO) {
	    // 토큰을 무효화 처리하는 로직 구현
	    // 여기에서는 예시로 token을 null로 설정합니다.
	    memberDTO.setToken(null);

	    return ResponseEntity.ok().body(memberDTO);
	}

	
	/*
	// 로그아웃 시 토큰 무효화
	public ResponseEntity<String> deleteToken(@RequestBody MemberDTO memberDTO) {
		기존 토큰
		String myToken = memberDTO.getToken();
		
		try {
			
			// 기존 토큰 파싱
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
					.build()
					.parseClaimsJws(myToken);
			
			Claims claims = jws.getBody();
			
			// 토큰 기한을 현재보다 이전 시간으로 설정
			Date expireDate = Date.from(
					Instant.now()
						.minus(1, ChronoUnit.HOURS));
			
			// 새로운 토큰 생성시켜서 기존 토큰을 무효화시킴
			String newToken = Jwts.builder()
					.setClaims(claims)
					.setExpiration(expireDate)
					.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
					.compact();
			
			memberDTO.setToken(null);		
			
			return ResponseEntity.ok().body("로그아웃이 완료되었습니다.");
			
		} catch (JwtException | IllegalArgumentException e) {
			// 토큰이 유효하지 않은 경우
			return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
		}
		
	}
	 */
}
