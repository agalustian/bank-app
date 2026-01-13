package ru.bank.notifications.dto;

import jakarta.validation.constraints.NotNull;

public record NotificationDTO(@NotNull String text, @NotNull String username) {
}
