package com.letsTravel.LetsTravel.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig {

	private final DataSource dataSource;

	@Autowired
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:5173") // Vite 개발 서버 주소
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
			}
		};
	}

}
