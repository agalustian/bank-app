package ru.bank.transfer.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bank.chassis.dto.NotificationDTO;
import ru.bank.transfer.models.NotificationOutbox;
import ru.bank.transfer.repositories.NotificationsOutboxJpaRepository;

@Service
public class NotificationsOutboxProcessor {

  @Value("${NOTIFICATIONS_OUTBOX_LIMIT:10}")
  private Integer limit = 10;

  @Value("${spring.kafka.producer.topics.notifications}")
  private String notificationsTopic;

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  private final Logger logger = LoggerFactory.getLogger(NotificationsOutboxProcessor.class);

  private final KafkaTemplate kafkaTemplate;

  public NotificationsOutboxProcessor(KafkaTemplate kafkaTemplate, NotificationsOutboxJpaRepository notificationsOutboxJpaRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
  }

  @Scheduled(fixedDelayString = "PT1s")
  public void processBySchedule() {
    process();
  }

  public void process() {
    var outboxNotifications =
        notificationsOutboxJpaRepository.findAll(PageRequest.of(0, limit).withSort(Sort.by("id"))).getContent();

    List<Long> processedIds = new ArrayList<>();
    for (NotificationOutbox notificationOutbox: outboxNotifications) {
      try {
        kafkaTemplate.send(notificationsTopic, notificationOutbox.getUsername(), new NotificationDTO(notificationOutbox.getUsername(), notificationOutbox.getText()));
        processedIds.add(notificationOutbox.getId());
      } catch (RuntimeException e) {
        logger.error("Exception occurred on sending notifications to: {}, error: {}", notificationOutbox.getUsername(), e.getStackTrace());
      }
    }

    logger.debug("Successfully sent ids: {}", processedIds);

    notificationsOutboxJpaRepository.deleteAllByIdInBatch(processedIds);
  }

}