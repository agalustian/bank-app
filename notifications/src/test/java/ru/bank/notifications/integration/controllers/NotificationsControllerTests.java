package ru.bank.notifications.integration.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.notifications.controllers.NotificationsController;
import ru.bank.notifications.dto.NotificationDTO;
import ru.bank.notifications.services.NotificationsService;

@WebMvcTest(NotificationsController.class)
class NotificationsControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private NotificationsService notificationsService;

  private String toJSON(NotificationDTO notificationDTO) {
    try {
      return objectMapper.writeValueAsString(notificationDTO);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  @Test
  @WithMockUser(authorities = "service")
  void shouldSendNotification() throws Exception {
    var notificationDTO = toJSON(new NotificationDTO("test", "username"));

    mockMvc.perform(post("/v1/notifications/send").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(notificationDTO))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldThrowUnauthorizedError() throws Exception {
    var notificationDTO = toJSON(new NotificationDTO("test", "username"));

    mockMvc.perform(post("/v1/notifications/send").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(notificationDTO))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(authorities = "service")
  void shouldThrowAnErrorOnEmptyText() throws Exception {
    var notificationDTO = toJSON(new NotificationDTO(null, "username"));

    mockMvc.perform(post("/v1/notifications/send").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(notificationDTO))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @WithMockUser(authorities = "service")
  void shouldThrowAnErrorOnEmptyUsername() throws Exception {
    var notificationDTO = toJSON(new NotificationDTO(null, "username"));

    mockMvc.perform(post("/v1/notifications/send").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(notificationDTO))
        .andExpect(status().is4xxClientError());
  }


}
