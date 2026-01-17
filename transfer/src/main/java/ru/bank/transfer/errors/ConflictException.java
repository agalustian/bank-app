package ru.bank.transfer.errors;

public class ConflictException extends RuntimeException {

  public ConflictException(String message) {
    super(message);
  }

}
