package ru.bank.notifications.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bank.notifications.models.Notification;
import ru.bank.notifications.services.NotificationsService;

@Service
public class NotificationsConsumer {

  private static final Logger logger = LoggerFactory.getLogger(NotificationsConsumer.class);

  @Autowired
  private NotificationsService notificationsService;

  @Autowired
  private ObjectMapper objectMapper;

  @KafkaListener(topics = "notifications", groupId = "bank-notifications-service-group")
  public void processNotification(ConsumerRecord<String, Notification> record) {
    logger.info("Получено сообщение: ключ [{}], значение [{}]", record.key(), record.value());

    notificationsService.sendNotification(record.value());
  }

}