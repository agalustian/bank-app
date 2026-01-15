package ru.bank.transfer.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bank.transfer.models.DepositOutbox;
import ru.bank.transfer.models.NotificationOutbox;
import ru.bank.transfer.repositories.DepositOutboxJpaRepository;
import ru.bank.transfer.repositories.NotificationsOutboxJpaRepository;

@Service
public class DepositOutboxProcessor {

  @Value("${NOTIFICATIONS_OUTBOX_LIMIT:10}")
  private Integer limit = 10;

  private final DepositOutboxJpaRepository depositOutboxJpaRepository;

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  private final AccountsService accountsService;

  private final Logger logger = LoggerFactory.getLogger(DepositOutboxProcessor.class);

  public DepositOutboxProcessor(DepositOutboxJpaRepository depositOutboxJpaRepository,
                                NotificationsOutboxJpaRepository notificationsOutboxJpaRepository,
                                AccountsService accountsService) {
    this.depositOutboxJpaRepository = depositOutboxJpaRepository;
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
    this.accountsService = accountsService;
  }

  @Scheduled(fixedDelayString = "PT1s")
  public void processBySchedule() {
    process();
  }

  public void process() {
    // TODO use sorting
    var outboxDeposits =
        depositOutboxJpaRepository.findAll(PageRequest.of(0, limit).withSort(Sort.by("id"))).getContent();

    List<Long> processedIds = new ArrayList<>();
    for (DepositOutbox outboxDeposit : outboxDeposits) {
      try {
        var account = accountsService.getAccountByLogin(outboxDeposit.getTo());

        accountsService.deposit(account, outboxDeposit.getAmount());
        notificationsOutboxJpaRepository.save(new NotificationOutbox("Deposit money success", account.getFullname()));
        processedIds.add(outboxDeposit.getId());
      } catch (RuntimeException e) {
        logger.error("Exception occurred on deposit to: {}, error: {}", outboxDeposit.getTo(), e.getStackTrace());
      }
    }

    logger.debug("Successfully sent ids: {}", processedIds);

    depositOutboxJpaRepository.deleteAllByIdInBatch(processedIds);
  }

}