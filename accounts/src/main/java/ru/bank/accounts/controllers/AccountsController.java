package ru.bank.accounts.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.accounts.services.AccountsService;
import ru.bank.accounts.dto.AccountDTO;
import ru.bank.accounts.dto.AccountShortInfoDTO;
import ru.bank.accounts.utils.SecurityUtils;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsController {

  @Autowired
  private AccountsService accountsService;

  @GetMapping("")
  ResponseEntity<AccountDTO> getAccount() {
    return ResponseEntity.ok(accountsService.getAccountByLogin(SecurityUtils.getLogin()));
  }

  @GetMapping("/{login}")
  @PreAuthorize("hasRole('SERVICE')")
  ResponseEntity<AccountDTO> getAccountByLogin(@PathVariable String login) {
    return ResponseEntity.ok(accountsService.getAccountByLogin(login));
  }

  @GetMapping("/list")
  ResponseEntity<List<AccountShortInfoDTO>> listAccountShortInfo() {
    return ResponseEntity.ok(accountsService.listAccountsShortInfo());
  }

  @PutMapping()
  ResponseEntity<AccountDTO> updateAccount(@RequestBody @Validated AccountDTO accountDTO) {
    return ResponseEntity.ok(accountsService.updateAccountByLogin(SecurityUtils.getLogin(), accountDTO));
  }

}
