package ru.bank.gateway.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CashController {

  @Value("${services.cash.url}")
  private String cashURL;

  @PutMapping("/cash/deposit")
  public ResponseEntity<?> deposit(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri(cashURL + path).put();
  }

  @PutMapping("/cash/withdrawal")
  public ResponseEntity<?> withdrawal(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri(cashURL + path).put();
  }

}
