package ru.bank.transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.bank.transfer.notifications.client.ApiClient;
import ru.bank.transfer.notifications.client.api.NotificationsServiceApi;

@Configuration
public class NotificationServiceClientConfig {

  @Bean
  NotificationsServiceApi notificationsControllerApi(RestTemplate restTemplate) {
    var client = new ApiClient(restTemplate);
    // TODO use from env
    client.setBasePath("http://localhost:8082");

    return new NotificationsServiceApi(client);
  }

}
