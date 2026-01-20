package ru.bank.transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  public RestClientConfig() {
  }

  @Bean
  public RestClient restClient(RestClient.Builder restClientBuilder) {
    return restClientBuilder.requestInterceptor(clientHttpRequestInterceptor()).build();
  }

  private ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
    return (request, body, execution) -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication != null && authentication.getCredentials() instanceof OAuth2Token token) {
        request.getHeaders().setBearerAuth(token.getTokenValue());
      }

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