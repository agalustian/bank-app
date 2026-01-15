package ru.bank.gateway.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountsController {

  @Value("${services.accounts.url}")
  private String accountsURL;

  @GetMapping("/accounts")
  public ResponseEntity<?> getAccount(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.sensitive().uri(accountsURL + path).get();
  }

  @GetMapping("/accounts/list")
  public ResponseEntity<?> getAccountsShortInfo(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.sensitive().uri(accountsURL + path).get();
  }

  @PutMapping("/accounts")
  public ResponseEntity<?> updateAccount(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.sensitive().uri(accountsURL + path).put();
  }

}
