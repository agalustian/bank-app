package ru.bank.transfer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications_outbox")
public class DepositOutbox {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "from")
  private String from;

  @Column(name = "to")
  private String to;

  @Column(name = "amount")
  private Integer amount;

  public DepositOutbox(String from, String to, Integer amount) {
    this.from = from;
    this.to = to;
    this.amount = amount;
  }

  public DepositOutbox(Long id, String from, String to, Integer amount) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.amount = amount;
  }

  public DepositOutbox() {}

  public Long getId() {
    return id;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public Integer getAmount() {
    return amount;
  }
}
