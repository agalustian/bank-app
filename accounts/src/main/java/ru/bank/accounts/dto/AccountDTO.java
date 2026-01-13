package ru.bank.accounts.dto;

import ru.bank.accounts.models.Account;

public record AccountDTO(String id, String fullname, String birthdate, Integer amount) {

  public static AccountDTO from(Account account) {
    return new AccountDTO(account.getId(), account.getFullname(), account.getBirthdate(),
        account.getAmount());
  }

}
