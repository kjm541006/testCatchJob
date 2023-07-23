//package com.project.catchJob.security;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Base64;
//import java.util.Date;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.project.catchJob.domain.member.Member;
//import com.project.catchJob.dto.member.MemberDTO;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//
//@RequiredArgsConstructor
//@Service
//public class TokenProvider_찐 {
//	
//	// 강사님 코드 lec02_todo_auth 참고
//	// https://velog.io/@kjy0302014/Spring-Security-%EC%97%86%EC%9D%B4-Spring-JWT-%EC%9D%B8%EC%A6%9D-%EC%9D%B8%EA%B0%80-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0 참고
//	// https://velog.io/@jkijki12/Spirng-Security-Jwt-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
//	// https://velog.io/@junho5336/SpringBoot-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-with.-SpringSecurity-JWT
//	
//	private String SECRET_KEY = "catchJobSecretKeycatchJobSecretKeycatchJobSecretKeycatchJobSecretKeycatchJobSecretKey";
//	
//	// 객체 초기화, SECRET_KEY를 Base64로 인코딩
//	@PostConstruct
//	protected void init() {
//		SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
//	}
//	
//	// 로그인 시 토큰 생성
//	public String createToken(Member member) {
//		// 기한 지금으로부터 1일로 설정
//		Date expireDate = Date.from(
//			Instant.now()
//				.plus(1, ChronoUnit.DAYS));
//		Claims claims = Jwts.claims().setSubject(member.getEmail());
//		
//		return Jwts.builder()
//				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//				.setSubject(member.getEmail()) // sub 토큰제목(토큰에서 사용자에 대한 식별 값)
//				.setIssuer("Token by catchJob") // iss 토큰발급자
//				.setIssuedAt(new Date()) // iat 토큰발급시간
//				.setExpiration(expireDate) // exp 토큰만료시간
//				.setClaims(claims) // 정보 저장 (사용자email)
//				.compact();
//	}
//	
//	// JWT 토큰에서 인증 정보 조회
//	public Authentication getAuthentication(String token, UserDetailsService userDetailsService) {
//		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
//		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//	}
//	
//	// 토큰에서 회원 정보 추출
//	public String getUserPk(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//	}
//	
//	// Request의 Header에서 token 값 가져오기 "Authorization" : "TOKEN값"
//	public String resolveToken(HttpServletRequest request) {
//		return request.getHeader("Authorization");
//	}
//	
//	// 토큰의 유효성/만료일자 확인
//	public boolean validateToken(String token) {
//		try {
//			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//			return !claimsJws.getBody().getExpiration().before(new Date());
//		} catch (Exception e) {
//			return false;
//		}
//	}
//	
//	/*
//	 * 	private static final String SECRET_KEY = "catchJobSecretKeycatchJobSecretKeycatchJobSecretKeycatchJobSecretKeycatchJobSecretKey";
//	
//	// 로그인 시 토큰 생성
//	public String createToken(Member member) {
//		// 기한 지금으로부터 1일로 설정
//		Date expireDate = Date.from(
//			Instant.now()
//				.plus(1, ChronoUnit.DAYS));
//		
//		return Jwts.builder()
//				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
//				.setSubject(member.getEmail()) // sub 토큰제목(토큰에서 사용자에 대한 식별 값)
//				.setIssuer("Token by catchJob") // iss 토큰발급자
//				.setIssuedAt(new Date()) // iat 토큰발급시간
//				.setExpiration(expireDate) // exp 토큰만료시간
//				.claim("member_id", member.getMemberId()) // member_id 저장
//				.compact();
//	}
//	// 토큰 검증
//	public String validateToken(String token) {
//		Claims claims = Jwts.parserBuilder()
//							.setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
//							.build().parseClaimsJws(token)
//							.getBody();
//		log.info("claims.getSubject = " + claims.getSubject());
//		
//		return claims.getSubject();
//	}
//	
//	// 로그아웃 시 토큰 무효화
//	public ResponseEntity<?> deleteToken(@RequestBody MemberDTO memberDTO) {
//	    // 토큰을 무효화 처리하는 로직 구현
//	    // 여기에서는 예시로 token을 null로 설정합니다.
//	    memberDTO.setToken(null);
//
//	    return ResponseEntity.ok().body(memberDTO);
//	}
//	*/
//	
//	/*
//	// 로그아웃 시 토큰 무효화
//	public ResponseEntity<String> deleteToken(@RequestBody MemberDTO memberDTO) {
//		기존 토큰
//		String myToken = memberDTO.getToken();
//		
//		try {
//			
//			// 기존 토큰 파싱
//			Jws<Claims> jws = Jwts.parserBuilder()
//					.setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
//					.build()
//					.parseClaimsJws(myToken);
//			
//			Claims claims = jws.getBody();
//			
//			// 토큰 기한을 현재보다 이전 시간으로 설정
//			Date expireDate = Date.from(
//					Instant.now()
//						.minus(1, ChronoUnit.HOURS));
//			
//			// 새로운 토큰 생성시켜서 기존 토큰을 무효화시킴
//			String newToken = Jwts.builder()
//					.setClaims(claims)
//					.setExpiration(expireDate)
//					.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
//					.compact();
//			
//			memberDTO.setToken(null);		
//			
//			return ResponseEntity.ok().body("로그아웃이 완료되었습니다.");
//			
//		} catch (JwtException | IllegalArgumentException e) {
//			// 토큰이 유효하지 않은 경우
//			return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
//		}
//		
//	}
//	 */
//}
