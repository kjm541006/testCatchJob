package com.project.catchJob.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.project.catchJob.domain.Member;

import io.jsonwebtoken.Claims;
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
				.compact();
	}

	public String validateToken(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
							.build().parseClaimsJws(token)
							.getBody();
		log.info("claims.getSubject = " + claims.getSubject());
		
		return claims.getSubject();
	}
}
