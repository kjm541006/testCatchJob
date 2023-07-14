package com.project.catchJob.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.project.catchJob.service.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalOauth2UserService principalOath;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.anyRequest().permitAll() // 권한 상관없이 모든 페이지 접근 가능
		.and()
			.oauth2Login() // Oauth2기반 로그인인 경우
			// .loginPage("/login") // 인증 필요한 url에 접근하면 /login으로 이동. 지금 위에서 permitAll해서 주석처리
			.defaultSuccessUrl("/") // 로그인 성공하면 "/"으로 이동
			.failureUrl("/login") // 로그인 실패하면 /login으로 이동
			.userInfoEndpoint() // 로그인 성공 후 사용자정보를 가져옴
			.userService(principalOath); // 사용자정보를 처리할 때 사용

			
	}
	
}
