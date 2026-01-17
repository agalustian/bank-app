package ru.bank.cash.dto;

import jakarta.validation.constraints.Positive;

public record EditMoneyDTO(@Positive Integer amount) {
}
