package ru.bank.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import ru.bank.accounts.models.Account;

public record AccountDTO(UUID id, @NotBlank String login, @NotBlank String fullname, @NotBlank String birthdate, @Positive Integer amount) {

  public static AccountDTO from(Account account) {
    return new AccountDTO(account.getId(), account.getLogin(), account.getFullname(),
        account.getBirthdate().format(DateTimeFormatter.ISO_LOCAL_DATE),
        account.getAmount());
  }

}
