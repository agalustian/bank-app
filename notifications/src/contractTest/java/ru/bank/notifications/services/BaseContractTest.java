package ru.bank.notifications.services;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
public abstract class BaseContractTest {

  @Autowired
  protected MockMvc mockMvc;

  @MockitoBean
  protected NotificationsService notificationsService;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.mockMvc(mockMvc);

  }
}