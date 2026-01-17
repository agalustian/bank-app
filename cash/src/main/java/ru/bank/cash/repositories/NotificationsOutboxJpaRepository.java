package ru.bank.cash.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.cash.models.NotificationOutbox;

@Repository
public interface NotificationsOutboxJpaRepository extends JpaRepository<NotificationOutbox, Long> {
}
