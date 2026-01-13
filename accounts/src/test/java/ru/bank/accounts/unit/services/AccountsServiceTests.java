package ru.bank.accounts.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.bank.accounts.dto.AccountDTO;
import ru.bank.accounts.dto.AccountShortInfoDTO;
import ru.bank.accounts.errors.NotFoundException;
import ru.bank.accounts.models.Account;
import ru.bank.accounts.models.NotificationOutbox;
import ru.bank.accounts.repositories.AccountsJpaRepository;
import ru.bank.accounts.repositories.NotificationsOutboxJpaRepository;
import ru.bank.accounts.services.AccountsService;

class AccountsServiceTests {

  private final AccountsJpaRepository accountsJpaRepository = Mockito.mock(AccountsJpaRepository.class);
  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository =
      Mockito.mock(NotificationsOutboxJpaRepository.class);

  private final AccountsService accountsService =
      new AccountsService(accountsJpaRepository, notificationsOutboxJpaRepository);

  private Account generateAccount() {
    return new Account(UUID.randomUUID(), "test", "fullname", LocalDate.now(), 10000);
  }

  @Nested
  class GetAccountByLoginTests {

    @Test
    void shouldGetAccountByLogin() {
      var account = generateAccount();

      Mockito.when(accountsJpaRepository.findAccountByLogin("test")).thenReturn(Optional.of(account));

      var foundAccount = accountsService.getAccountByLogin("test");

      assertEquals(AccountDTO.from(account), foundAccount);
    }

    @Test
    void shouldThrowAnErrorOnUnknownLogin() {
      var account = generateAccount();

      Mockito.when(accountsJpaRepository.findAccountByLogin("test")).thenReturn(Optional.of(account));

      assertThrows(NotFoundException.class, () -> accountsService.getAccountByLogin("unknown"), "Account not found");
    }

    @Test
    void shouldThrowAnErrorOnNullableLogin() {
      assertThrows(IllegalArgumentException.class, () -> accountsService.getAccountByLogin(null));
    }

  }

  @Nested
  class GetAccountsShortInfoTests {

    @Test
    void shouldGetAccountByLogin() {
      var account = generateAccount();

      Mockito.when(accountsJpaRepository.findAll()).thenReturn(List.of(account));

      var foundAccountsShortInfo = accountsService.listAccountsShortInfo();

      assertEquals(List.of(AccountShortInfoDTO.from(account)), foundAccountsShortInfo);
    }

  }

  @Nested
  class UpdateAccountTests {

    @Test
    void shouldUpdateAccount() {
      var account = generateAccount();
      var updatePayload = new AccountDTO(account.getId(), account.getFullname(), account.getBirthdate().format(
          DateTimeFormatter.ISO_LOCAL_DATE), 1);

      Mockito.when(accountsJpaRepository.findAccountByLogin("test")).thenReturn(Optional.of(account));
      Mockito.when(accountsJpaRepository.save(any(Account.class))).thenReturn(
          new Account(updatePayload.id(), account.getLogin(), updatePayload.fullname(),
              LocalDate.parse(updatePayload.birthdate()),
              updatePayload.amount()
          )
      );

      var foundAccount = accountsService.updateAccountByLogin("test", updatePayload);

      assertEquals(updatePayload, foundAccount);
    }

    @Test
    void shouldSaveToOutbox() {
      var account = generateAccount();
      var updatePayload = new AccountDTO(account.getId(), account.getFullname(), account.getBirthdate().format(
          DateTimeFormatter.ISO_LOCAL_DATE), 1);

      Mockito.when(accountsJpaRepository.findAccountByLogin("test")).thenReturn(Optional.of(account));

      accountsService.updateAccountByLogin("test", updatePayload);

      verify(notificationsOutboxJpaRepository, times(1)).save(any(NotificationOutbox.class));
    }

    @Test
    void shouldThrowAnErrorOnUnknownLogin() {
      var account = generateAccount();

      Mockito.when(accountsJpaRepository.findAccountByLogin("test")).thenReturn(Optional.of(account));

      assertThrows(NotFoundException.class,
          () -> accountsService.updateAccountByLogin("unknown", AccountDTO.from(account)), "Account not found");
    }

    @Test
    void shouldThrowAnConflictError() {
      var account = new Account(UUID.randomUUID(), "test", "fullname", LocalDate.now(), 1000);
      var dto = new AccountDTO(UUID.randomUUID(), "fullname", "1982-10-11", 1000);

      Mockito.when(accountsJpaRepository.findAccountByLogin("test")).thenReturn(Optional.of(account));

      assertThrows(NotFoundException.class, () -> accountsService.updateAccountByLogin("unknown", dto),
          "Account not found");
    }

  }

}
