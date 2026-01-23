package ru.bank.cash.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bank.cash.accounts.client.api.AccountsServiceApi;
import ru.bank.cash.accounts.domain.AccountDTO;

@Service
public class AccountsService {

  private final Logger logger = LoggerFactory.getLogger(AccountsService.class);

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

    accountsServiceApi.updateAccount(account.getLogin(), accountDTO);

    logger.info("Deposit successful to {}", account.getLogin());
  }

  public void withdrawal(final AccountDTO account, final Integer amount) {
    var accountDTO = new AccountDTO();
    accountDTO.setId(account.getId());
    accountDTO.setFullname(account.getFullname());
    accountDTO.setBirthdate(account.getBirthdate());
    accountDTO.setAmount(account.getAmount() - amount);

    accountsServiceApi.updateAccount(account.getLogin(), accountDTO);

    logger.info("Withdrawal successful for {}", account.getLogin());
  }

  public AccountDTO getAccount() {
    return accountsServiceApi.getAccount();
  }

}
