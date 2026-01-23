package ru.bank.transfer.unit.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import ru.bank.chassis.dto.NotificationDTO;
import ru.bank.transfer.models.NotificationOutbox;
import ru.bank.transfer.repositories.NotificationsOutboxJpaRepository;
import ru.bank.transfer.services.NotificationsOutboxProcessor;

class NotificationsOutboxProcessorTests {

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository = Mockito.mock(
      NotificationsOutboxJpaRepository.class);

  private final KafkaTemplate kafkaTemplate = Mockito.mock(KafkaTemplate.class);

  private final NotificationsOutboxProcessor notificationsOutboxProcessor =
      new NotificationsOutboxProcessor(kafkaTemplate, notificationsOutboxJpaRepository);

  @Test
  void shouldSendNotification() {

    var notifications = List.of(new NotificationOutbox(1L, "test text", "username"));
    when(notificationsOutboxJpaRepository.findAll(any(PageRequest.class))).thenReturn(
        new PageImpl<>(notifications, PageRequest.of(0, 10), notifications.size()));

    notificationsOutboxProcessor.process();

    verify(notificationsOutboxJpaRepository, times(1)).findAll(any(PageRequest.class));
    verify(kafkaTemplate, times(1)).send(null, "username",
        new NotificationDTO("username", "test text"));
    verify(notificationsOutboxJpaRepository, times(1)).deleteAllByIdInBatch(List.of(1L));
  }

}
