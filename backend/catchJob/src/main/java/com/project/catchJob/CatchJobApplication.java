package com.project.catchJob;

import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class CatchJobApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CatchJobApplication.class);
        app.setDefaultProperties(getProperties());
        app.run(args);
    }

<<<<<<< HEAD
	    return objectMapper;
	}
	
=======
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CatchJobApplication.class);
    }

    static Properties getProperties() {
        Properties props = new Properties();
        props.put("spring.config.additional-location", "file:/home/ubuntu/catchJob/backend/");
        return props;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module(); 
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        objectMapper.registerModule(hibernate5Module);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }
    
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Bean
        public CommonsMultipartResolver multipartResolver() {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
            multipartResolver.setMaxUploadSize(20971520); 
            return multipartResolver;
        }
    }
}


//import java.util.Properties;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module; // 이 부분을 추가하세요.
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@SpringBootApplication
//public class CatchJobApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(CatchJobApplication.class, args);
//	}
//	
//	@Bean
//	public ObjectMapper objectMapper() {
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    Hibernate5Module hibernate5Module = new Hibernate5Module(); // 수정된 대문자 'H'
//	    hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
//	    objectMapper.registerModule(hibernate5Module);
//	    objectMapper.registerModule(new JavaTimeModule());
//
//	    return objectMapper;
//	}
//	
>>>>>>> 8a5fa7f1c951aa545590e08cd823e7e813269155
//	@Configuration
//	public class WebConfig implements WebMvcConfigurer {
//		// 멀티파트 해결자 빈 등록
//		@Bean
//		public CommonsMultipartResolver multipartResolver() {
//            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
<<<<<<< HEAD
//            return multipartResolver;
//        }
//	}
}
=======
//            multipartResolver.setMaxUploadSize(20971520); // 20MB 설정
//            return multipartResolver;
//        }
//	}
//
//}
>>>>>>> 8a5fa7f1c951aa545590e08cd823e7e813269155
