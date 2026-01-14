package ru.bank.cash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.bank.accounts.notifications.client.ApiClient;
import ru.bank.accounts.notifications.client.api.NotificationsControllerApi;

@Configuration
public class NotificationServiceClientConfig {

  @Bean
  ApiClient apiClient(RestTemplate restTemplate) {
    var client = new ApiClient(restTemplate);
    // TODO use from env
    client.setBasePath("http://localhost:8082");

    return client;
  }

  @Bean
  NotificationsControllerApi notificationsControllerApi(ApiClient apiClient) {
    return new NotificationsControllerApi();
  }

}
