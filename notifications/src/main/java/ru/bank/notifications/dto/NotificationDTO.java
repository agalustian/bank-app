package ru.bank.notifications.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Нотификация")
public record NotificationDTO(@NotBlank String text, @NotBlank String username) {
}
