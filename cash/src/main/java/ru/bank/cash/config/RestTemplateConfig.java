package ru.bank.cash.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  public RestTemplateConfig() {
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return  builder.additionalInterceptors(clientHttpRequestInterceptor()).build();
  }

  private ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
    return (request, body, execution) -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication instanceof JwtAuthenticationToken oauth2Token) {
        Jwt accessToken = oauth2Token.getToken();

        if (accessToken != null) {
          request.getHeaders().setBearerAuth(accessToken.getTokenValue());

          return execution.execute(request, body);
        }
      }

      return execution.execute(request, body);
    };
  }

}