package ru.bank.accounts.integration.repositories;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.bank.accounts.integration.PostgreSQLTestContainer;
import ru.bank.accounts.models.Account;
import ru.bank.accounts.repositories.AccountsJpaRepository;

@SpringBootTest
@Testcontainers
@ImportTestcontainers({PostgreSQLTestContainer.class})
class AccountsRepositoryTests {

  @Autowired
  private AccountsJpaRepository accountsJpaRepository;

  @Test
  void shouldFindAccountByLogin() {
    var account = accountsJpaRepository.save(new Account(UUID.randomUUID(), "test", "fullname", LocalDate.parse("1980-11-23"), 10000));

    var foundAccount = accountsJpaRepository.findAccountByLogin("test").get();

    Assertions.assertEquals(foundAccount, account);
  }

}
