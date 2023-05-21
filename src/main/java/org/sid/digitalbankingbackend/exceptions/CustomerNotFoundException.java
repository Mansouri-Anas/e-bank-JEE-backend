package org.sid.digitalbankingbackend.exceptions;


/*
RuntimeException : Exception Non surveillée
Exception : Exception surveillée
*/

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String customerNotFound) {
        super(customerNotFound);
    }
}
