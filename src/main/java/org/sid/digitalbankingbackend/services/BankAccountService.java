package org.sid.digitalbankingbackend.services;

import org.sid.digitalbankingbackend.entities.BankAccount;
import org.sid.digitalbankingbackend.entities.CurrentAccount;
import org.sid.digitalbankingbackend.entities.Customer;
import org.sid.digitalbankingbackend.entities.SavingAccount;
import org.sid.digitalbankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.digitalbankingbackend.exceptions.CustomerNotFoundException;
import org.sid.digitalbankingbackend.exceptions.InsufficientBalanceException;

import java.util.Currency;
import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);

    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<Customer> listCustomers();

    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String desc) throws BankAccountNotFoundException, InsufficientBalanceException;

    void credit(String accountId, double amount, String desc) throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, InsufficientBalanceException;


    List<BankAccount> bankAccountList();
}
