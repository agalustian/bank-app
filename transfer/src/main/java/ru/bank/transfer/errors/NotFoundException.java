package ru.bank.transfer.errors;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

}
