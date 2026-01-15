package ru.bank.transfer.services;

import org.springframework.stereotype.Service;
import ru.bank.transfer.accounts.client.api.AccountsServiceApi;
import ru.bank.transfer.accounts.domain.AccountDTO;

@Service
public class ServiceAccountService {

  private final AccountsServiceApi serviceAccountsServiceApi;

  public ServiceAccountService(AccountsServiceApi serviceAccountsServiceApi) {
    this.serviceAccountsServiceApi = serviceAccountsServiceApi;
  }

  public void deposit(final AccountDTO account, final Integer amount) {
    var accountDTO = new AccountDTO();
    accountDTO.setId(account.getId());
    accountDTO.setFullname(account.getFullname());
    accountDTO.setBirthdate(account.getBirthdate());
    accountDTO.setAmount(account.getAmount() + amount);

    serviceAccountsServiceApi.updateAccount(account.getLogin(), accountDTO);

  }

  public AccountDTO getAccountByLogin(final String login) {
    return serviceAccountsServiceApi.getAccountByLogin(login);
  }

}
