package ru.bank.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bank.accounts.models.NotificationOutbox;

public interface NotificationsOutboxJpaRepository extends JpaRepository<NotificationOutbox, Long> {
}
