package ru.bank.cash.unit.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.bank.cash.accounts.domain.AccountDTO;
import ru.bank.cash.models.NotificationOutbox;
import ru.bank.cash.repositories.NotificationsOutboxJpaRepository;
import ru.bank.cash.services.AccountsService;
import ru.bank.cash.services.CashService;

class CashServiceTests {

  private final AccountsService accountsService = Mockito.mock(AccountsService.class);
  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository =
      Mockito.mock(NotificationsOutboxJpaRepository.class);

  private final CashService cashService =
      new CashService(notificationsOutboxJpaRepository, accountsService);

  private AccountDTO generateAccountDTO() {
    var accountDTO = new AccountDTO();

    accountDTO.setId(UUID.randomUUID());
    accountDTO.setFullname("fullname");
    accountDTO.setBirthdate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    accountDTO.setAmount(10000);

    return accountDTO;
  }

  @Test
  void shouldDepositMoney() {
    var accountDTO = generateAccountDTO();

    Mockito.when(accountsService.getAccount()).thenReturn(accountDTO);

    cashService.depositMoney(1000);

    verify(accountsService, times(1)).deposit(accountDTO, 1000);
    verify(notificationsOutboxJpaRepository, times(1)).save(any(NotificationOutbox.class));
  }

  @Test
  void shouldWithdrawalMoney() {
    var accountDTO = generateAccountDTO();

    Mockito.when(accountsService.getAccount()).thenReturn(accountDTO);

    cashService.withdrawalMoney(1000);

    verify(accountsService, times(1)).withdrawal(accountDTO, 1000);
    verify(notificationsOutboxJpaRepository, times(1)).save(any(NotificationOutbox.class));
  }

}
