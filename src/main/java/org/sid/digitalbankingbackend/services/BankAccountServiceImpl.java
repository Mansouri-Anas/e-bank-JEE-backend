package org.sid.digitalbankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.digitalbankingbackend.entities.*;
import org.sid.digitalbankingbackend.enums.AccountStatus;
import org.sid.digitalbankingbackend.enums.OperationType;
import org.sid.digitalbankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.digitalbankingbackend.exceptions.CustomerNotFoundException;
import org.sid.digitalbankingbackend.exceptions.InsufficientBalanceException;
import org.sid.digitalbankingbackend.repositories.AccountOperationRepository;
import org.sid.digitalbankingbackend.repositories.BankAccountRepository;
import org.sid.digitalbankingbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j //To log messages
public class BankAccountServiceImpl implements BankAccountService {
    //    @Autowired
    private CustomerRepository customerRepository;
    //    @Autowired
    private BankAccountRepository bankAccountRepository;
    //    @Autowired
    private AccountOperationRepository accountOperationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) throw new CustomerNotFoundException("Customer Not Found");
        else {
            CurrentAccount bankAccount = new CurrentAccount();
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            bankAccount.setBalance(initialBalance);
            bankAccount.setCustomer(customer);
            bankAccount.setStatus(AccountStatus.CREATED);
            bankAccount.setOverDraft(overDraft);
            CurrentAccount savedAccount = bankAccountRepository.save(bankAccount);
            return savedAccount;
        }
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) throw new CustomerNotFoundException("Customer Not Found");
        else {
            SavingAccount bankAccount = new SavingAccount();
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            bankAccount.setBalance(initialBalance);
            bankAccount.setCustomer(customer);
            bankAccount.setStatus(AccountStatus.CREATED);
            bankAccount.setInterestRate(interestRate);
            SavingAccount savedBankAccount = bankAccountRepository.save(bankAccount);
            return savedBankAccount;
        }
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(() ->new BankAccountNotFoundException("BankAccount Not Found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String desc) throws BankAccountNotFoundException, InsufficientBalanceException {
        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance()<amount) throw new InsufficientBalanceException("Insufficiant Balance");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(desc);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String desc) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(desc);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, InsufficientBalanceException {
        debit(accountIdSource,amount,"Transfer to"+getBankAccount(accountIdDestination).getCustomer().getName());
        credit(accountIdDestination,amount,"Transfer from "+getBankAccount(accountIdSource).getCustomer().getName());

    }

    @Override
    public List<BankAccount> bankAccountList() {
        return bankAccountRepository.findAll();
    }
}
