package ru.bank.gateway.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TransferController {

  @Value("${services.transfer.url}")
  private String transferURL;

  @PutMapping("/transfer/**")
  public ResponseEntity<?> transfer(ProxyExchange<byte[]> proxy) {
    String path = proxy.path("/api");
    return proxy.sensitive().uri(transferURL + path).put();
  }

}
