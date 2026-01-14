package ru.bank.gateway.controllers;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountsController {

  @GetMapping("/accounts")
  public ResponseEntity<?> getAccount(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri("http://localhost:8084" + path).get();
  }

  @GetMapping("/accounts/list")
  public ResponseEntity<?> getAccountsShortInfo(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri("http://localhost:8084" + path).get();
  }

  @PutMapping("/accounts")
  public ResponseEntity<?> updateAccount(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri("http://localhost:8084" + path).put();
  }

}
