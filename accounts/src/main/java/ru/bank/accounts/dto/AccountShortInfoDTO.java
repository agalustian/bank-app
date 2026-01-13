package ru.bank.accounts.dto;

import ru.bank.accounts.models.Account;

public record AccountShortInfoDTO(String login, String fullname) {

  public static AccountShortInfoDTO from(Account account) {
    return new AccountShortInfoDTO(account.getLogin(), account.getFullname());
  }

}
