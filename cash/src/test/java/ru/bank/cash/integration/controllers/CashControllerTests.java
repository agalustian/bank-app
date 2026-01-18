package ru.bank.cash.integration.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.cash.controllers.CashController;
import ru.bank.cash.dto.EditMoneyDTO;
import ru.bank.cash.services.CashService;

@WebMvcTest(CashController.class)
class CashControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CashService cashService;

  private String toJSON(EditMoneyDTO editMoneyDTO) {
    try {
      return objectMapper.writeValueAsString(editMoneyDTO);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  @Test
  @WithMockUser(roles = "CASH")
  void shouldDepositMoney() throws Exception {
    var editMoneyDTO = new EditMoneyDTO(100);

    mockMvc.perform(put("/v1/cash/deposit").with(csrf()).content(toJSON(editMoneyDTO)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldThrowAnErrorOnDeposit() throws Exception {
    var editMoneyDTO = new EditMoneyDTO(100);

    mockMvc.perform(put("/v1/cash/deposit").with(csrf()).content(toJSON(editMoneyDTO)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "CASH")
  void shouldWithdrawalMoney() throws Exception {
    var editMoneyDTO = new EditMoneyDTO(100);

    mockMvc.perform(put("/v1/cash/withdrawal").with(csrf()).content(toJSON(editMoneyDTO)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldThrowAnErrorOnWithdrawal() throws Exception {
    var editMoneyDTO = new EditMoneyDTO(100);

    mockMvc.perform(put("/v1/cash/withdrawal").with(csrf()).content(toJSON(editMoneyDTO)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

}
