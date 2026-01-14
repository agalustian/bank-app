package ru.bank.cash.services;

import org.springframework.stereotype.Service;

@Service
public class CashService {

  private final NotificationsService notificationsService;

  private final AccountsService accountsService;

  public CashService(NotificationsService notificationsService, AccountsService accountsService) {
    this.notificationsService = notificationsService;
    this.accountsService = accountsService;
  }

  public void depositMoney(final String login, final Integer amount) {
    var account = accountsService.getAccount(login);

    accountsService.deposit(account, amount);
    notificationsService.sendNotification(account.getFullname(),
        "Deposit for user" + account.getFullname() + "successfully loaded");
  }

  public void withdrawalMoney(final String login, final Integer amount) {
    var account = accountsService.getAccount(login);

    accountsService.withdrawal(account, amount);
    notificationsService.sendNotification(account.getFullname(),
        "Withdrawal for user" + account.getFullname() + "successfully loaded");
  }

}
