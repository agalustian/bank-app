package ru.bank.accounts.contract;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ru.bank.accounts.notifications.client.ApiClient;
import ru.bank.accounts.notifications.client.api.NotificationsServiceApi;
import ru.bank.accounts.notifications.domain.NotificationDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
    ids = "ru.bank:notifications:+:stubs:8082",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class NotificationsServiceContractTests {

  @Autowired
  private NotificationsServiceApi notificationsServiceApi;

  @Test
  void shouldMatchContractWhenSendingNotification() {
    var notificationDTO = new NotificationDTO();
    notificationDTO.setUsername("test username");
    notificationDTO.setText("test text");

    notificationsServiceApi.setApiClient(new ApiClient(RestClient.builder().defaultHeader("Authorization", "Bearer test").build()));
    String result = notificationsServiceApi.sendNotification(notificationDTO);

    assertNull(result);
  }

}