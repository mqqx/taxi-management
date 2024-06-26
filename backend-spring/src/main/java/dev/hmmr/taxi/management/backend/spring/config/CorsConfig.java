package dev.hmmr.taxi.management.backend.spring.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsConfig {
  @Value("${taxi-management.allowed-origins}")
  String allowedOrigins;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods(
                HEAD.name(),
                GET.name(),
                POST.name(),
                PUT.name(),
                DELETE.name(),
                PATCH.name(),
                OPTIONS.name())
            .allowedOrigins(allowedOrigins.split(","));
      }
    };
  }
}
