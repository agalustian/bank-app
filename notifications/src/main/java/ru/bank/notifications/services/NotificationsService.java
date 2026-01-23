package ru.bank.notifications.services;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bank.chassis.dto.NotificationDTO;

@Service
public class NotificationsService {

  private static final Logger logger = LoggerFactory.getLogger(NotificationsService.class);

  private final MeterRegistry meterRegistry;

  public NotificationsService(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void sendNotification(NotificationDTO notification) {
    if (notification == null) {
      meterRegistry.counter("send_notification_error", "type", "logger").increment();
      return;
    }

    meterRegistry.counter("send_notification_total", "type", "logger").increment();

    logger.info("Notification for '{}' with text '{}'", notification.username(), notification.text());
  }

}
