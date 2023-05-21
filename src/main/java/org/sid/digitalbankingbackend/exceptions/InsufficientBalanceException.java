package org.sid.digitalbankingbackend.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String insufficiantBalance) {
        super(insufficiantBalance);
    }
}
