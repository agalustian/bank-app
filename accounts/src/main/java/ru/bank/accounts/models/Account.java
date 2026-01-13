package ru.bank.accounts.models;

//логин пользователя (аккаунт связывается с вошедшим пользователем по этому логину и логину из JWT);
//фамилия и имя;
//дата рождения;
//количество денег на счету.

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "accounts")
public class Account {

  @Id
  private String id;

  @Column(name = "login", nullable = false, updatable = false)
  private String login;

  @Column(name = "fullname", nullable = false, updatable = false)
  private String fullname;

  @Column(name = "birthdate", nullable = false, updatable = false)
  private String birthdate;

  @Column(name = "amount", nullable = false)
  private Integer amount;

  public Account(String login, String fullname, String birthdate, Integer amount) {
    this.login = login;
    this.fullname = fullname;
    this.birthdate = birthdate;
    this.amount = amount;
  }

  public Account() {}

  public String getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getFullname() {
    return fullname;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public Integer getAmount() {
    return amount;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(id, account.id) && Objects.equals(login, account.login) &&
        Objects.equals(fullname, account.fullname) && Objects.equals(birthdate, account.birthdate) &&
        Objects.equals(amount, account.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, fullname, birthdate, amount);
  }
}
