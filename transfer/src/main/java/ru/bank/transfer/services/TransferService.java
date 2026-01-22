package ru.bank.transfer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.transfer.dto.TransferDTO;
import ru.bank.transfer.models.DepositOutbox;
import ru.bank.transfer.models.NotificationOutbox;
import ru.bank.transfer.repositories.DepositOutboxJpaRepository;
import ru.bank.transfer.repositories.NotificationsOutboxJpaRepository;

@Service
public class TransferService {

  private final Logger logger = LoggerFactory.getLogger(TransferService.class);

  private final AccountsService accountsService;

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  private final DepositOutboxJpaRepository depositOutboxJpaRepository;

  public TransferService(AccountsService accountsService,
                         NotificationsOutboxJpaRepository notificationsOutboxJpaRepository,
                         DepositOutboxJpaRepository depositOutboxJpaRepository
  ) {
    this.accountsService = accountsService;
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
    this.depositOutboxJpaRepository = depositOutboxJpaRepository;
  }

  @Transactional
  public void transfer(String login, TransferDTO transferDTO) {
    var fromAccount = accountsService.getAccount();

    accountsService.withdrawal(fromAccount, transferDTO.amount());
    notificationsOutboxJpaRepository.save(
        new NotificationOutbox("Withdrawal money success", fromAccount.getFullname()));

    depositOutboxJpaRepository.save(new DepositOutbox(login, transferDTO.to(), transferDTO.amount()));

    logger.info("Transfer successful for: {}", login);
  }

}
