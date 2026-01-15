package ru.bank.transfer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestClient;
import ru.bank.transfer.notifications.client.ApiClient;
import ru.bank.transfer.notifications.client.api.NotificationsServiceApi;

@Configuration
public class NotificationServiceClientConfig {
  @Value("${services.notifications.url}")
  private String notificationsURL;

  @Bean
  NotificationsServiceApi notificationsControllerApi(RestClient serviceRestClient) {
    var client = new ApiClient(serviceRestClient);
    client.setBasePath(notificationsURL);

    return new NotificationsServiceApi(client);
  }

}
