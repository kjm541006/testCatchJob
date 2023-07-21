package com.project.catchJob.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {/* implements WebMvcConfigurer {
	
	private String resourcePath = "/upload/**"; // view에서 접근할 경로
	private String savePath = "classpath:/static/upload/";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourcePath)
			.addResourceLocations(savePath);
	}*/
}