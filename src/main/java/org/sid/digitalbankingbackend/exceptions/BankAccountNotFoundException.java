package org.sid.digitalbankingbackend.exceptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String bankAccountNotFound) {
    super(bankAccountNotFound);
    }
}
