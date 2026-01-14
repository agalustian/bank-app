package ru.bank.transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.bank.transfer.accounts.client.ApiClient;
import ru.bank.transfer.accounts.client.api.AccountsServiceApi;

@Configuration
public class AccountsServiceClientConfig {

  @Bean
  AccountsServiceApi accountsServiceApi(RestTemplate restTemplate) {
    var client = new ApiClient(restTemplate);
    // TODO use from env
    client.setBasePath("http://localhost:8083");

    return new AccountsServiceApi(client);
  }

}
