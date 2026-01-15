package ru.bank.accounts.dto;

import java.time.format.DateTimeFormatter;
import java.util.UUID;
import ru.bank.accounts.models.Account;

public record AccountDTO(UUID id, String login, String fullname, String birthdate, Integer amount) {

  public static AccountDTO from(Account account) {
    return new AccountDTO(account.getId(), account.getLogin(), account.getFullname(),
        account.getBirthdate().format(DateTimeFormatter.ISO_LOCAL_DATE),
        account.getAmount());
  }

}
