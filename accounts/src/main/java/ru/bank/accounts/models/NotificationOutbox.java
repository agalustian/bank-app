package ru.bank.accounts.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "notifications_outbox")
public class NotificationOutbox {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text")
  private String text;

  @Column(name = "username")
  private String username;

  public NotificationOutbox(String text, String username) {
    this.text = text;
    this.username = username;
  }
  public NotificationOutbox(Long id, String text, String username) {
    this.text = text;
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public String getUsername() {
    return username;
  }

}
