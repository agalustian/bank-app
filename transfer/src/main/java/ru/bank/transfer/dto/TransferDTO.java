package ru.bank.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TransferDTO(@NotBlank String to, @Positive Integer amount) {
}
