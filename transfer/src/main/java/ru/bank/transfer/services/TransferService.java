package ru.bank.transfer.services;

import org.springframework.stereotype.Service;
import ru.bank.transfer.dto.TransferDTO;
import ru.bank.transfer.models.NotificationOutbox;
import ru.bank.transfer.repositories.NotificationsOutboxJpaRepository;

@Service
public class TransferService {

  private final AccountsService accountsService;

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  public TransferService(AccountsService accountsService, NotificationsOutboxJpaRepository notificationsOutboxJpaRepository) {
    this.accountsService = accountsService;
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
  }

  public void transfer(TransferDTO transferDTO) {
    var fromAccount = accountsService.getAccount(transferDTO.from());
    var toAccount = accountsService.getAccount(transferDTO.to());

    accountsService.withdrawal(fromAccount, transferDTO.amount());
    notificationsOutboxJpaRepository.save(new NotificationOutbox("Withdrawal money success", fromAccount.getFullname()));

    accountsService.deposit(toAccount, transferDTO.amount());
    notificationsOutboxJpaRepository.save(new NotificationOutbox("Deposit money success", fromAccount.getFullname()));
  }

}
