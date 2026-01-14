package ru.bank.transfer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.transfer.models.DepositOutbox;

@Repository
public interface DepositOutboxJpaRepository extends JpaRepository<DepositOutbox, Long> {
}
