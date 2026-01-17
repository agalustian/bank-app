package ru.bank.cash.services;

import org.springframework.stereotype.Service;
import ru.bank.cash.models.NotificationOutbox;
import ru.bank.cash.repositories.NotificationsOutboxJpaRepository;

@Service
public class CashService {

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  private final AccountsService accountsService;

  public CashService(NotificationsOutboxJpaRepository notificationsOutboxJpaRepository,
                     AccountsService accountsService) {
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
    this.accountsService = accountsService;
  }

  public void depositMoney(final Integer amount) {
    var account = accountsService.getAccount();

    accountsService.deposit(account, amount);
    notificationsOutboxJpaRepository.save(
        new NotificationOutbox("Deposit for user" + account.getFullname() + "successfully processed",
            account.getFullname()));
  }

  public void withdrawalMoney(final Integer amount) {
    var account = accountsService.getAccount();

    accountsService.withdrawal(account, amount);
    notificationsOutboxJpaRepository.save(
        new NotificationOutbox("Withdrawal for user" + account.getFullname() + "successfully processed",
            account.getFullname()));
  }

}
