package ru.bank.front.controller;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bank.front.accounts.domain.AccountDTO;

import ru.bank.front.dto.CashAction;
import ru.bank.front.services.AccountService;

@Controller
public class MainController {
  @Autowired
  private AccountService accountService;

  @GetMapping
  public String index() {
    return "redirect:/account";
  }

  @GetMapping("/account")
  public String getAccount(Model model) {
    // TODO: Заменить на то, что описано в комментарии к методу
    var login = "test";

    var accountDTO = accountService.getAccount(login);

    fillModel(login, accountDTO, model, null, null);

    return "main";
  }

  @PostMapping("/account")
  public String editAccount(
      Model model,
      @RequestParam("fullname") String fullname,
      @RequestParam("birthdate") LocalDate birthdate
  ) {
    var login = "test";
    var updatedAccount =
        accountService.updateAccount(login, fullname, birthdate.format(DateTimeFormatter.ISO_LOCAL_DATE));

    fillModel(login, updatedAccount, model, null, null);

    return "main";
  }

  @PostMapping("/cash")
  public String editCash(
      Model model,
      @RequestParam("amount") int amount,
      @RequestParam("action") CashAction action
  ) {
    var login = "test";
    var accountDTO = accountService.editCash(login, amount, action);

    fillModel(login, accountDTO, model, null, null);

    return "main";
  }

  @PostMapping("/transfer")
  public String transfer(
      Model model,
      @RequestParam("value") int value,
      @RequestParam("toAccount") String toAccount
  ) {
    var login = "test";

    var transferResult = accountService.transfer(value, login, toAccount);

    fillModel(login, transferResult.accountDTO(), model, transferResult.errors(), transferResult.message());

    return "main";
  }

  public void fillModel(String login, AccountDTO accountDTO, Model model,
                        @Nullable List<String> errors, @Nullable String info) {

    var accountsShortInfo = accountService.listAccountsShortInfo().stream()
        .filter(accountShortInfoDTO -> !Objects.equals(accountShortInfoDTO.getLogin(), login)
        ).toList();

    model.addAttribute("fullname", accountDTO.getFullname());
    model.addAttribute("birthdate", accountDTO.getBirthdate());
    model.addAttribute("sum", accountDTO.getAmount());
    model.addAttribute("accounts", accountsShortInfo);
    model.addAttribute("errors", errors);
    model.addAttribute("info", info);
  }
}
