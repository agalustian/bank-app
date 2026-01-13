package ru.bank.accounts.unit.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.bank.accounts.models.NotificationOutbox;
import ru.bank.accounts.repositories.NotificationsOutboxJpaRepository;
import ru.bank.accounts.services.NotificationsOutboxProcessor;
import ru.bank.accounts.services.NotificationsService;

class NotificationsOutboxProcessorTests {

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository = Mockito.mock(
      NotificationsOutboxJpaRepository.class);

  private final NotificationsService notificationsService = Mockito.mock(NotificationsService.class);

  private final NotificationsOutboxProcessor notificationsOutboxProcessor =
      new NotificationsOutboxProcessor(notificationsOutboxJpaRepository, notificationsService);

  @Test
  void shouldSendNotification() {

    var notifications = List.of(new NotificationOutbox(1L, "test text", "username"));
    when(notificationsOutboxJpaRepository.findAll(any(PageRequest.class))).thenReturn(
        new PageImpl<>(notifications, PageRequest.of(0, 10), notifications.size()));

    notificationsOutboxProcessor.process();

    verify(notificationsOutboxJpaRepository, times(1)).findAll(any(PageRequest.class));
    verify(notificationsService, times(1)).sendNotification("username", "test text");
    verify(notificationsOutboxJpaRepository, times(1)).deleteAllByIdInBatch(List.of(1L));
  }

  @Test
  void shouldDeleteOnlySuccessfullySentNotifications() {
    var notifications = List.of(
        new NotificationOutbox(1L, "test text", "username"),
        new NotificationOutbox(2L, "another test text", "another username")
    );
    when(notificationsOutboxJpaRepository.findAll(any(PageRequest.class))).thenReturn(
        new PageImpl<>(notifications, PageRequest.of(0, 10), notifications.size()));

    when(notificationsService.sendNotification("another username", "another test text")).thenThrow(new RuntimeException("alarm!"));

    notificationsOutboxProcessor.process();

    verify(notificationsOutboxJpaRepository, times(1)).deleteAllByIdInBatch(List.of(1L));
  }

}
