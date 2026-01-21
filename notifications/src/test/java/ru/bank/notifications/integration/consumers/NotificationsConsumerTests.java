package ru.bank.notifications.integration.consumers;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.bank.chassis.dto.NotificationDTO;
import ru.bank.notifications.services.NotificationsService;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    topics = {NotificationsConsumerTests.TEST_TOPIC_NAME}
)
public class NotificationsConsumerTests {

  public static final String TEST_TOPIC_NAME = "notifications";

  @Autowired
  private KafkaTemplate<String, NotificationDTO> kafkaTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private NotificationsService notificationsService;

  @Test
  public void shouldProcessNotificationMessage() {
    var testNotification = new NotificationDTO("test-text", "test-username");
    kafkaTemplate.send(TEST_TOPIC_NAME, "Yandex", testNotification);

    await()
        .atMost(5, TimeUnit.SECONDS)
        .pollInterval(100, TimeUnit.MILLISECONDS)
        .untilAsserted(() -> {
          verify(notificationsService, times(1)).sendNotification(testNotification);
        });
  }

}