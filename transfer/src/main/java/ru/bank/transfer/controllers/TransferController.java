package ru.bank.transfer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.transfer.dto.TransferDTO;
import ru.bank.transfer.services.TransferService;
import ru.bank.transfer.utils.SecurityUtils;

@RestController
@RequestMapping("/v1/transfer")
public class TransferController {

  @Autowired
  private TransferService transferService;

  @PutMapping
  ResponseEntity<String> transferMoney(@RequestBody @Validated TransferDTO transferDTO) {
    transferService.transfer(SecurityUtils.getLogin(), transferDTO);

    return ResponseEntity.noContent().build();
  }

}
