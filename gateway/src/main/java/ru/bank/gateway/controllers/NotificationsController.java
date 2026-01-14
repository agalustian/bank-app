package ru.bank.gateway.controllers;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class NotificationsController {

  @PostMapping("/notifications/send")
  public ResponseEntity<?> sendNotification(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.uri("http://localhost:8082" + path).post();
  }

}