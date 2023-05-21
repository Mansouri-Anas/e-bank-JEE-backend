package org.sid.digitalbankingbackend;

import org.sid.digitalbankingbackend.entities.AccountOperation;
import org.sid.digitalbankingbackend.entities.CurrentAccount;
import org.sid.digitalbankingbackend.entities.Customer;
import org.sid.digitalbankingbackend.entities.SavingAccount;
import org.sid.digitalbankingbackend.enums.AccountStatus;
import org.sid.digitalbankingbackend.enums.OperationType;
import org.sid.digitalbankingbackend.repositories.AccountOperationRepository;
import org.sid.digitalbankingbackend.repositories.BankAccountRepository;
import org.sid.digitalbankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan","Ayman","Youness").forEach(name-> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i = 0; i < 10; i++) {
                AccountOperation accountOperation=new AccountOperation();
                accountOperation.setBankAccount(bankAccount);
                accountOperation.setOperationDate(new Date());
                accountOperation.setAmount(Math.random()*12000);
                accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
