package ru.bank.accounts.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bank.accounts.models.NotificationOutbox;
import ru.bank.accounts.repositories.NotificationsOutboxJpaRepository;

@Service
public class NotificationsOutboxProcessor {

  @Value("${NOTIFICATIONS_OUTBOX_LIMIT:10}")
  private Integer limit = 10;

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  private final NotificationsService notificationsService;

  private final Logger logger = LoggerFactory.getLogger(NotificationsOutboxProcessor.class);

  public NotificationsOutboxProcessor(NotificationsOutboxJpaRepository notificationsOutboxJpaRepository,
                                      NotificationsService notificationsService) {
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
    this.notificationsService = notificationsService;
  }

  @Scheduled(fixedDelayString = "PT1s")
  public void processBySchedule() {
    process();
  }

  public void process() {
    // TODO use sorting
    var outboxNotifications =
        notificationsOutboxJpaRepository.findAll(PageRequest.of(0, limit).withSort(Sort.by("id"))).getContent();

    List<Long> processedIds = new ArrayList<>();
    for (NotificationOutbox notificationOutbox: outboxNotifications) {
      try {
        notificationsService.sendNotification(notificationOutbox.getUsername(), notificationOutbox.getText());
        processedIds.add(notificationOutbox.getId());
      } catch (RuntimeException e) {
        logger.error("Exception occurred on sending notifications to: {}, error: {}", notificationOutbox.getUsername(), e.getStackTrace());
      }
    }

    logger.debug("Successfully sent ids: {}", processedIds);

    notificationsOutboxJpaRepository.deleteAllByIdInBatch(processedIds);
  }

}