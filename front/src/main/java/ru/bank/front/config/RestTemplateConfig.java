package ru.bank.front.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  private final OAuth2AuthorizedClientService authorizedClientService;

  public RestTemplateConfig(OAuth2AuthorizedClientService authorizedClientService) {
    this.authorizedClientService = authorizedClientService;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return  builder.additionalInterceptors(clientHttpRequestInterceptor()).build();
  }

  private ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
    return (request, body, execution) -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
            oauth2Token.getAuthorizedClientRegistrationId(),
            oauth2Token.getName()
        );

        OAuth2AccessToken accessToken = authorizedClient != null ? authorizedClient.getAccessToken() : null;

        if (accessToken != null) {
          request.getHeaders().setBearerAuth(accessToken.getTokenValue());

          return execution.execute(request, body);
        }
      }

      return execution.execute(request, body);
    };
  }

}