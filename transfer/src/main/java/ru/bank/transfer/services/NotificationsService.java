package ru.bank.transfer.services;

import org.springframework.stereotype.Service;
import ru.bank.transfer.notifications.client.api.NotificationsServiceApi;
import ru.bank.transfer.notifications.domain.NotificationDTO;

@Service
public class NotificationsService {

  private final NotificationsServiceApi notificationsServiceApi;

  public NotificationsService(NotificationsServiceApi notificationsServiceApi) {
    this.notificationsServiceApi = notificationsServiceApi;
  }

  public String sendNotification(String username, String text) {
    var notification = new NotificationDTO();
    notification.setUsername(username);
    notification.setText(text);

    return notificationsServiceApi.sendNotification(notification);
  }

}
