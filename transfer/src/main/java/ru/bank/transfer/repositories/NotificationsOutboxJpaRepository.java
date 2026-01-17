package ru.bank.transfer.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.transfer.models.NotificationOutbox;

@Repository
public interface NotificationsOutboxJpaRepository extends JpaRepository<NotificationOutbox, Long> {
}
