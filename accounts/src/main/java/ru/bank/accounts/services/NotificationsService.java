package ru.bank.accounts.services;

import org.springframework.stereotype.Service;
import ru.bank.accounts.notifications.client.api.NotificationsServiceApi;
import ru.bank.accounts.notifications.domain.NotificationDTO;

@Service
public class NotificationsService {

  private final NotificationsServiceApi notificationsControllerApi;

  public NotificationsService(NotificationsServiceApi notificationsControllerApi) {
    this.notificationsControllerApi = notificationsControllerApi;
  }

  public String sendNotification(String username, String text) {
    var notification = new NotificationDTO();
    notification.setUsername(username);
    notification.setText(text);

    return notificationsControllerApi.sendNotification(notification);
  }

}
