package ru.bank.transfer.unit.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.micrometer.core.instrument.MeterRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.bank.transfer.accounts.domain.AccountDTO;
import ru.bank.transfer.dto.TransferDTO;
import ru.bank.transfer.models.DepositOutbox;
import ru.bank.transfer.models.NotificationOutbox;
import ru.bank.transfer.repositories.DepositOutboxJpaRepository;
import ru.bank.transfer.repositories.NotificationsOutboxJpaRepository;
import ru.bank.transfer.services.AccountsService;
import ru.bank.transfer.services.TransferService;

class TransferServiceTests {

  private final AccountsService accountsService = Mockito.mock(AccountsService.class);

  private final MeterRegistry meterRegistry = Mockito.mock(MeterRegistry.class);

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository =
      Mockito.mock(NotificationsOutboxJpaRepository.class);

  private final DepositOutboxJpaRepository depositOutboxJpaRepository =
      Mockito.mock(DepositOutboxJpaRepository.class);

  private final TransferService transferService =
      new TransferService(accountsService, notificationsOutboxJpaRepository, depositOutboxJpaRepository, meterRegistry);

  private AccountDTO generateAccountDTO() {
    var accountDTO = new AccountDTO();

    accountDTO.setId(UUID.randomUUID());
    accountDTO.setFullname("fullname");
    accountDTO.setBirthdate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    accountDTO.setAmount(10000);

    return accountDTO;
  }

  @Test
  void shouldTransferMoney() {
    var fromAccountDTO = generateAccountDTO();

    Mockito.when(accountsService.getAccount()).thenReturn(fromAccountDTO);

    transferService.transfer("from", new TransferDTO("to", 1000));

    verify(accountsService, times(1)).withdrawal(fromAccountDTO, 1000);
    verify(notificationsOutboxJpaRepository, times(1)).save(any(NotificationOutbox.class));
    verify(depositOutboxJpaRepository, times(1)).save(any(DepositOutbox.class));
  }

}
