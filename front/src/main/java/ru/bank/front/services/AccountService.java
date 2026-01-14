package ru.bank.front.services;

import org.springframework.stereotype.Service;
import ru.bank.front.accounts.client.api.AccountsServiceApi;
import ru.bank.front.accounts.domain.AccountDTO;
import ru.bank.front.accounts.domain.AccountShortInfoDTO;
import ru.bank.front.cash.client.api.CashServiceApi;
import ru.bank.front.cash.domain.EditMoneyDTO;
import ru.bank.front.dto.CashAction;
import ru.bank.front.dto.TransferResult;
import ru.bank.front.transfer.client.api.TransferServiceApi;

import java.util.List;
import ru.bank.front.transfer.domain.TransferDTO;

@Service
public class AccountService {

  private AccountsServiceApi accountsServiceApi;

  private CashServiceApi cashServiceApi;

  private TransferServiceApi transferServiceApi;

  public AccountService(AccountsServiceApi accountsServiceApi,
                        CashServiceApi cashServiceApi,
                        TransferServiceApi transferServiceApi
  ) {
    this.accountsServiceApi = accountsServiceApi;
    this.cashServiceApi = cashServiceApi;
    this.transferServiceApi = transferServiceApi;
  }

  public AccountDTO getAccount(final String login) {
    return accountsServiceApi.getAccount();
  }

  public AccountDTO updateAccount(final String login, final String fullname, final String birthdate) {
    var accountDTO = getAccount(login);
    accountDTO.setFullname(fullname);
    accountDTO.setBirthdate(birthdate);

    return accountsServiceApi.updateAccount(accountDTO);
  }

  public List<AccountShortInfoDTO> listAccountsShortInfo() {
    return accountsServiceApi.listAccountShortInfo();
  }

  public AccountDTO editCash(String login, int amount, CashAction action) {
    var amountDTO = new EditMoneyDTO();
    amountDTO.setAmount(amount);

    switch (action) {
      case GET -> cashServiceApi.withdrawalMoney(amountDTO);
      case PUT -> cashServiceApi.depositMoney(amountDTO);
    }

    return getAccount(login);
  }

  public TransferResult transfer(int value, String login, String toAccount) {
    var transferDTO = new TransferDTO();
    transferDTO.setFrom(login);
    transferDTO.setTo(toAccount);

    var accountDTO = getAccount(login);
    String message = null;
    List<String> errors = null;
    try {
      transferServiceApi.transferMoney(transferDTO);
      message = "Успешно переведено %d руб клиенту %s".formatted(value, accountDTO.getFullname());
    } catch (RuntimeException e) {
      errors = List.of("Недостаточно средств на счету");
    }

    return new TransferResult(accountDTO, message, errors);
  }

}
