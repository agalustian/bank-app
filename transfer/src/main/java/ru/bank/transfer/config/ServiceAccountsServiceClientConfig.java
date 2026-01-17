package ru.bank.transfer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.bank.transfer.accounts.client.ApiClient;
import ru.bank.transfer.accounts.client.api.AccountsServiceApi;

@Configuration
public class ServiceAccountsServiceClientConfig {

  @Value("${services.accounts.url}")
  private String accountsURL;

  @Bean
  AccountsServiceApi serviceAccountsServiceApi(RestClient serviceRestClient) {
    var client = new ApiClient(serviceRestClient);
    client.setBasePath(accountsURL);

    return new AccountsServiceApi(client);
  }

}
