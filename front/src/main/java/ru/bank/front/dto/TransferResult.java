package ru.bank.front.dto;

import java.util.List;
import ru.bank.front.accounts.domain.AccountDTO;

public record TransferResult(AccountDTO accountDTO, String message, List<String> errors) {
}
