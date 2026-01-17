package ru.bank.accounts.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.bank.accounts.models.Account;

public interface AccountsJpaRepository extends JpaRepository<Account, Integer> {

  Optional<Account> findAccountByLogin(String login);

}
