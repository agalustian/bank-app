package ru.bank.notifications.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.bank.notifications.models.Notification;
import ru.bank.notifications.services.NotificationsService;

@Component
public class NotificationsConsumer {

  private static final Logger logger = LoggerFactory.getLogger(NotificationsConsumer.class);

  @Autowired
  private NotificationsService notificationsService;

  @KafkaListener(topics = "notifications")
  public void print(ConsumerRecord<?, ?> record) {
    logger.info("Получено сообщение: ключ [{}], значение [{}]", record.key(), record.value());

    // TODO fixme
    notificationsService.sendNotification(new Notification(record.value().toString(), record.value().toString()));
  }

}