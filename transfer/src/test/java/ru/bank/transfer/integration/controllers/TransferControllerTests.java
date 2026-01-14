package ru.bank.transfer.integration.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.transfer.controllers.TransferController;
import ru.bank.transfer.dto.TransferDTO;
import ru.bank.transfer.services.TransferService;

@WebMvcTest(TransferController.class)
class TransferControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private TransferService transferService;

  private String toJSON(TransferDTO transferDTO) {
    try {
      return objectMapper.writeValueAsString(transferDTO);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  @Test
  void shouldTransferMoney() throws Exception {
    var transferDTO = new TransferDTO("from", "to", 100);

    mockMvc.perform(put("/v1/transfer").content(toJSON(transferDTO)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

}
