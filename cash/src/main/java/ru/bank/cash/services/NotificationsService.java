package ru.bank.cash.services;

import org.springframework.stereotype.Service;
import ru.bank.cash.notifications.client.api.NotificationsControllerApi;
import ru.bank.cash.notifications.domain.NotificationDTO;

@Service
public class NotificationsService {

  private final NotificationsControllerApi notificationsControllerApi;

  public NotificationsService(NotificationsControllerApi notificationsControllerApi) {
    this.notificationsControllerApi = notificationsControllerApi;
  }

  public String sendNotification(String username, String text) {
    var notification = new NotificationDTO();
    notification.setUsername(username);
    notification.setText(text);

    return notificationsControllerApi.sendNotification(notification);
  }

}
