package ru.bank.front.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.bank.front.accounts.client.api.AccountsServiceApi;
import ru.bank.front.cash.client.api.CashServiceApi;
import ru.bank.front.transfer.client.api.TransferServiceApi;

@Configuration
public class ServiceClientsConfig {

  @Value("${GATEWAY_PATH}")
  private String GATEWAY_PATH;

  @Bean
  AccountsServiceApi accountsServiceApi(RestTemplate restTemplate) {
    var client = new ru.bank.front.accounts.client.ApiClient(restTemplate);
    client.setBasePath(GATEWAY_PATH);

    return new AccountsServiceApi(client);
  }

  @Bean
  TransferServiceApi transferServiceApi(RestTemplate restTemplate) {
    var client = new ru.bank.front.transfer.client.ApiClient(restTemplate);
    client.setBasePath(GATEWAY_PATH);

    return new TransferServiceApi(client);
  }

  @Bean
  CashServiceApi cashServiceApi(RestTemplate restTemplate) {
    var apiClient = new ru.bank.front.cash.client.ApiClient(restTemplate);
    apiClient.setBasePath(GATEWAY_PATH);

    return new CashServiceApi(apiClient);
  }

}
