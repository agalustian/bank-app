package ru.bank.cash.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

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
    this.id = id;
    this.text = text;
    this.username = username;
  }

  public NotificationOutbox() {}

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotificationOutbox that = (NotificationOutbox) o;
    return Objects.equals(text, that.text) &&
        Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, username);
  }
}
