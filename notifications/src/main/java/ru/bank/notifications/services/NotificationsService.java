package ru.bank.notifications.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bank.notifications.models.Notification;

@Service
public class NotificationsService {

  private static final Logger logger = LoggerFactory.getLogger(NotificationsService.class);

  public void sendNotification(Notification notification) {
    if (notification == null) {
      return;
    }

    logger.info("Notification for '{}' with text '{}'", notification.username(), notification.text());
  }

}
