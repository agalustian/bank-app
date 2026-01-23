package ru.bank.accounts.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.bank.accounts.dto.AccountDTO;
import ru.bank.accounts.dto.AccountShortInfoDTO;
import ru.bank.accounts.errors.ConflictException;
import ru.bank.accounts.errors.NotFoundException;
import ru.bank.accounts.models.Account;
import ru.bank.accounts.models.NotificationOutbox;
import ru.bank.accounts.repositories.AccountsJpaRepository;
import ru.bank.accounts.repositories.NotificationsOutboxJpaRepository;

@Service
public class AccountsService {

  private final AccountsJpaRepository accountsJpaRepository;

  private final NotificationsOutboxJpaRepository notificationsOutboxJpaRepository;

  private final Logger logger = LoggerFactory.getLogger(AccountsService.class);

  public AccountsService(AccountsJpaRepository accountsJpaRepository,
                         NotificationsOutboxJpaRepository notificationsOutboxJpaRepository) {
    this.accountsJpaRepository = accountsJpaRepository;
    this.notificationsOutboxJpaRepository = notificationsOutboxJpaRepository;
  }

  private Account findOrThrowAccount(final String login) {
    return accountsJpaRepository.findAccountByLogin(login)
        .orElseThrow(() -> new NotFoundException("Account not found"));
  }

  public AccountDTO getAccountByLogin(final String login) {
    Assert.notNull(login, "login is required for getting account info");

    Account account = findOrThrowAccount(login);

    return AccountDTO.from(account);
  }

  public List<AccountShortInfoDTO> listAccountsShortInfo() {
    return accountsJpaRepository.findAll().stream().map(AccountShortInfoDTO::from).toList();
  }

  @Transactional
  public AccountDTO updateAccountByLogin(final String login, AccountDTO accountDTO) {
    Assert.notNull(login, "login is required for updating account info");

    Account account = findOrThrowAccount(login);

    if (!Objects.equals(account.getId(), accountDTO.id())) {
      throw new ConflictException("Can't to update account info");
    }

    if (!Objects.equals(account.getAmount(), accountDTO.amount())) {
      notificationsOutboxJpaRepository.save(new NotificationOutbox(account.getFullname(),
          "Account balance changed from" + account.getAmount() + "to" + accountDTO.amount()
      ));
    }

    var updated =
        new Account(accountDTO.id(), account.getLogin(), accountDTO.fullname(), LocalDate.parse(accountDTO.birthdate()),
            accountDTO.amount());

    accountsJpaRepository.save(updated);

    logger.info("Account updated for {}", login);

    return AccountDTO.from(updated);
  }

}
