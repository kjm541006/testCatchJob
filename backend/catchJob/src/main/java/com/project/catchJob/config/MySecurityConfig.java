////package com.project.catchJob.config;
//////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.context.annotation.Bean;
//////import org.springframework.context.annotation.Configuration;
//////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//////import org.springframework.security.crypto.password.PasswordEncoder;
//////import org.springframework.security.web.AuthenticationEntryPoint;
//////import org.springframework.web.cors.CorsConfiguration;
//////import org.springframework.web.cors.CorsConfigurationSource;
//////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//////
//////import java.util.Arrays;
//////
////////import com.project.catchJob.security.PasswordEncoder;
//////
//////@Configuration
//////public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//////
//////    @Autowired
//////    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
//////  
//////    @Autowired
//////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//////        auth.inMemoryAuthentication()
//////          .withUser("user").password(passwordEncoder().encode("password"))
//////          .authorities("ROLE_USER");
//////    }
//////  
//////    @Bean
//////    public PasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
//////  
//////    @Override
//////    protected void configure(HttpSecurity http) throws Exception {
//////        http.csrf().disable()
//////          .authorizeRequests()
//////            .antMatchers("/").permitAll()
//////            .anyRequest().authenticated()
//////            .and()
//////          .httpBasic()
//////            .authenticationEntryPoint(authenticationEntryPoint)
//////            .and()
//////          .cors(); // 이 부분을 추가하여 CORS 설정을 활성화합니다.
//////    }
//////  
//////    @Bean
//////    public CorsConfigurationSource corsConfigurationSource() {
//////        CorsConfiguration configuration = new CorsConfiguration();
//////        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//////        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//////        configuration.setAllowCredentials(true);
//////        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//////        source.registerCorsConfiguration("/**", configuration);
//////        return source;
//////    }
//////}
//////
////
////
////
////
////
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.AuthenticationEntryPoint;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.CorsConfigurationSource;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////import org.springframework.security.web.AuthenticationEntryPoint;
////
////import java.util.Arrays;
////
////@Configuration
////public class MySecurityConfig extends WebSecurityConfigurerAdapter {
////	
////	@Autowired
////    public MySecurityConfig(AuthenticationEntryPoint authenticationEntryPoint) {
////        this.authenticationEntryPoint = authenticationEntryPoint;
////    }
////
////    @Autowired
////    private AuthenticationEntryPoint authenticationEntryPoint;
////
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("user").password(passwordEncoder().encode("password"))
////                .authorities("ROLE_USER");
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .httpBasic()
////                .authenticationEntryPoint(authenticationEntryPoint)
////                .and()
////                .cors(); // 이 부분을 추가하여 CORS 설정을 활성화합니다.
////    }
////
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
////        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
////        configuration.setAllowCredentials(true);
////        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
////}
////
//
//
//
//
//
//package com.project.catchJob.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .cors();
//    }
//}
//
