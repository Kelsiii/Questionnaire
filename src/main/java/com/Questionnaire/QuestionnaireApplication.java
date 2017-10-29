package com.Questionnaire;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
@ServletComponentScan
@SpringBootApplication
public class QuestionnaireApplication extends SpringBootServletInitializer{  
	  
  
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QuestionnaireApplication.class, args);
    }

    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
        return builder.sources(this.getClass());  
    }  
  
} 
*/
@SpringBootApplication
public class QuestionnaireApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(QuestionnaireApplication.class, args);
	}
}

