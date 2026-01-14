package ru.bank.cash.services;

import org.springframework.stereotype.Service;
import ru.bank.cash.accounts.client.api.AccountsServiceApi;
import ru.bank.cash.accounts.domain.AccountDTO;

@Service
public class AccountsService {

  private final AccountsServiceApi accountsServiceApi;

  public AccountsService(AccountsServiceApi accountsServiceApi) {
    this.accountsServiceApi = accountsServiceApi;
  }

  public void deposit(final AccountDTO account, final Integer amount) {
    var accountDTO = new AccountDTO();
    accountDTO.setId(account.getId());
    accountDTO.setFullname(account.getFullname());
    accountDTO.setBirthdate(account.getBirthdate());
    accountDTO.setAmount(account.getAmount() + amount);

    accountsServiceApi.updateAccount(accountDTO);

  }

  public void withdrawal(final AccountDTO account, final Integer amount) {
    var accountDTO = new AccountDTO();
    accountDTO.setId(account.getId());
    accountDTO.setFullname(account.getFullname());
    accountDTO.setBirthdate(account.getBirthdate());
    accountDTO.setAmount(account.getAmount() - amount);

    accountsServiceApi.updateAccount(accountDTO);
  }

  public AccountDTO getAccount(final String login) {
    return accountsServiceApi.getAccount();
  }

}
