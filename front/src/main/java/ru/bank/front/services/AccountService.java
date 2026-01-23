package ru.bank.front.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bank.front.accounts.client.api.AccountsServiceApi;
import ru.bank.front.accounts.domain.AccountDTO;
import ru.bank.front.accounts.domain.AccountShortInfoDTO;
import ru.bank.front.cash.client.api.CashServiceApi;
import ru.bank.front.cash.domain.EditMoneyDTO;
import ru.bank.front.dto.CashAction;
import ru.bank.front.dto.EditCashResult;
import ru.bank.front.dto.TransferResult;
import ru.bank.front.transfer.client.api.TransferServiceApi;

import java.util.List;
import ru.bank.front.transfer.domain.TransferDTO;

@Service
public class AccountService {

  private final Logger logger = LoggerFactory.getLogger(AccountService.class);

  private final AccountsServiceApi accountsServiceApi;

  private final CashServiceApi cashServiceApi;

  private final TransferServiceApi transferServiceApi;

  public AccountService(AccountsServiceApi accountsServiceApi,
                        CashServiceApi cashServiceApi,
                        TransferServiceApi transferServiceApi
  ) {
    this.accountsServiceApi = accountsServiceApi;
    this.cashServiceApi = cashServiceApi;
    this.transferServiceApi = transferServiceApi;
  }

  public AccountDTO getAccount() {
    return accountsServiceApi.getAccount();
  }

  public AccountDTO updateAccount(final String login, final String fullname, final String birthdate) {
    var accountDTO = getAccount();
    accountDTO.setFullname(fullname);
    accountDTO.setBirthdate(birthdate);

    return accountsServiceApi.updateAccount(login, accountDTO);
  }

  public List<AccountShortInfoDTO> listAccountsShortInfo() {
    return accountsServiceApi.listAccountShortInfo();
  }

  public EditCashResult editCash(int amount, CashAction action) {
    var amountDTO = new EditMoneyDTO();
    amountDTO.setAmount(amount);

    var accountDTO = getAccount();
    String message = null;
    List<String> errors = null;
    try {
      switch (action) {
        case GET -> cashServiceApi.withdrawalMoney(amountDTO);
        case PUT -> cashServiceApi.depositMoney(amountDTO);
      }

      logger.info("Editing cash successfully for: {}", accountDTO.getLogin());
      message = "Успешно выполнено";
    } catch (RuntimeException e) {
      errors = List.of("Не удалось произвести операцию");
      logger.error("Exception during editing cash: {}", e.getMessage());
    }

    return new EditCashResult(accountDTO, message, errors);
  }

  public TransferResult transfer(int amount, String login, String toAccount) {
    var transferDTO = new TransferDTO();
    transferDTO.setTo(toAccount);
    transferDTO.setAmount(amount);

    var accountDTO = getAccount();
    String message = null;
    List<String> errors = null;
    try {
      transferServiceApi.transferMoney(login, transferDTO);
      message = "Успешно переведено %d руб клиенту %s".formatted(amount, accountDTO.getFullname());
      logger.info("Transfer successfully for: {}", accountDTO.getLogin());
    } catch (RuntimeException e) {
      errors = List.of("Недостаточно средств на счету");
      logger.error("Exception during transfer: {}", e.getMessage());
    }

    return new TransferResult(accountDTO, message, errors);
  }

}
