package ru.bank.gateway.controllers;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CashController {

  @PutMapping("/cash/deposit")
  public ResponseEntity<?> deposit(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri("http://localhost:8083" + path).put();
  }

  @PutMapping("/cash/withdrawal")
  public ResponseEntity<?> withdrawal(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri("http://localhost:8083" + path).put();
  }

}
