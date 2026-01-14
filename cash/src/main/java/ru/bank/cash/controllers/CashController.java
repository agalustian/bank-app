package ru.bank.cash.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.cash.dto.EditMoneyDTO;
import ru.bank.cash.services.CashService;

@RestController
@RequestMapping("/v1/cash")
public class CashController {

  @Autowired
  private CashService cashService;

  @PutMapping("/deposit")
  ResponseEntity<String> depositMoney(@RequestBody @Validated EditMoneyDTO editMoneyDTO) {
    var login = "stub";

    cashService.depositMoney(login, editMoneyDTO.amount());

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/withdrawal")
  ResponseEntity<String> withdrawalMoney(@RequestBody @Validated EditMoneyDTO editMoneyDTO) {
    var login = "stub";

    cashService.withdrawalMoney(login, editMoneyDTO.amount());

    return ResponseEntity.noContent().build();
  }

}
