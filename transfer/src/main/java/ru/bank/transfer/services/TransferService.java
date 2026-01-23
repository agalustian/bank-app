package ru.bank.transfer.services;

import io.micrometer.core.instrument.MeterRegistry;
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

  private final MeterRegistry meterRegistry;

  public TransferService(AccountsService accountsService,
                         NotificationsOutboxJpaRepository notificationsOutboxJpaRepository,
                         DepositOutboxJpaRepository depositOutboxJpaRepository,
                         MeterRegistry meterRegistry
  ) {
    this.accountsService = accountsService;
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
    this.depositOutboxJpaRepository = depositOutboxJpaRepository;
    this.meterRegistry = meterRegistry;
  }

  @Transactional
  public void transfer(String login, TransferDTO transferDTO) {
    try {
      var fromAccount = accountsService.getAccount();

      accountsService.withdrawal(fromAccount, transferDTO.amount());
      notificationsOutboxJpaRepository.save(
          new NotificationOutbox("Withdrawal money success", fromAccount.getFullname()));

      depositOutboxJpaRepository.save(new DepositOutbox(login, transferDTO.to(), transferDTO.amount()));

      logger.info("Transfer successful for: {}", login);
    } catch (RuntimeException e) {
      meterRegistry.counter("transfer_error", "from", login, "to", transferDTO.to()).increment();

      throw e;
    }
  }

}
