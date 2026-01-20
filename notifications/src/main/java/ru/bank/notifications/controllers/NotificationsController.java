package ru.bank.notifications.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.notifications.dto.NotificationDTO;
import ru.bank.notifications.models.Notification;
import ru.bank.notifications.services.NotificationsService;

@Profile("local")
@RestController
@RequestMapping("/v1/notifications")
public class NotificationsController {

  @Autowired
  private NotificationsService notificationsService;

  @PostMapping("/send")
  ResponseEntity<String> sendNotification(@RequestBody @Validated NotificationDTO dto) {
    notificationsService.sendNotification(new Notification(dto.text(), dto.username()));

    return ResponseEntity.noContent().build();
  }

}
