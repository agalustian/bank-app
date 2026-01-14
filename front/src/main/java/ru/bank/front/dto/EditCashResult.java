package ru.bank.front.dto;

import java.util.List;
import ru.bank.front.accounts.domain.AccountDTO;

public record EditCashResult(AccountDTO accountDTO, String message, List<String> errors) {
}
