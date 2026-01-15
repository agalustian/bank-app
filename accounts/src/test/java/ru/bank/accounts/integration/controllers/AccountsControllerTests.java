package ru.bank.accounts.integration.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;
import java.util.UUID;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;
import ru.bank.accounts.controllers.AccountsController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.accounts.dto.AccountDTO;
import ru.bank.accounts.dto.AccountShortInfoDTO;
import ru.bank.accounts.services.AccountsService;

@WebMvcTest(AccountsController.class)
class AccountsControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AccountsService accountsService;

  private String accountToJSON(AccountDTO accountDTO) {
    try {
      return objectMapper.writeValueAsString(accountDTO);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  private String accountShortInfoListToJSON(List<AccountShortInfoDTO> accountDTO) {
    try {
      return objectMapper.writeValueAsString(accountDTO);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  @Test
  void shouldGetAccount() throws Exception {
    var accountDTO = new AccountDTO(UUID.randomUUID(),  "test", "fullname", "1988-10-23", 10);

    when(accountsService.getAccountByLogin("stub")).thenReturn(accountDTO);

    MvcResult response = mockMvc.perform(get("/v1/accounts"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    assertEquals(accountToJSON(accountDTO), response.getResponse().getContentAsString());
  }

  @Test
  void shouldGetAccountsShortInfo() throws Exception {
    var accountShortInfoDTOList = List.of(new AccountShortInfoDTO("test", "fullname"));

    when(accountsService.listAccountsShortInfo()).thenReturn(accountShortInfoDTOList);

    MvcResult response = mockMvc.perform(get("/v1/accounts/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    assertEquals(accountShortInfoListToJSON(accountShortInfoDTOList), response.getResponse().getContentAsString());
  }

  @Test
  void shouldUpdateAccount() throws Exception {
    var accountDTO = new AccountDTO(UUID.randomUUID(), "test",  "fullname", "1988-10-23", 10);

    when(accountsService.updateAccountByLogin("stub", accountDTO)).thenReturn(accountDTO);

    mockMvc.perform(put("/v1/accounts").contentType(MediaType.APPLICATION_JSON).content(accountToJSON(accountDTO)))
        .andExpect(status().isOk());
  }

}
