package com.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.training.bean.ProgressBean;

@Configuration
public class AppMVCConfigure {

  @Bean
  WebMvcConfigurer mvcConfigurer() {
    return new WebMvcConfigurer() {

      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
            .allowedOrigins("*") // Configure the allowed origins (you can restrict to specific origins)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Configure the allowed HTTP methods
            .allowedHeaders("*") // Configure the allowed headers
            .maxAge(3600); // Configure the max age of the CORS pre-flight response in seconds
      }
    };
  }
  
 
  
  @Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
		
	}
//  @Bean
//  public CorsFilter corsFilter() {
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    CorsConfiguration config = new CorsConfiguration();
//    config.addAllowedOrigin("http://13.48.82.196:4200");
//    config.addAllowedHeader("*");
//    config.addAllowedMethod("*");
//    source.registerCorsConfiguration("/**", config);
//    return new CorsFilter(source);
//  }
}