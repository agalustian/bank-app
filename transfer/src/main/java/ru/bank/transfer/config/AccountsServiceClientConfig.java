package ru.bank.transfer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.bank.transfer.accounts.client.ApiClient;
import ru.bank.transfer.accounts.client.api.AccountsServiceApi;

@Configuration
public class AccountsServiceClientConfig {

  @Value("${services.accounts.url}")
  private String accountsURL;

  @Bean
  AccountsServiceApi accountsServiceApi(RestTemplate restTemplate) {
    var client = new ApiClient(restTemplate);
    client.setBasePath(accountsURL);

    return new AccountsServiceApi(client);
  }

}
