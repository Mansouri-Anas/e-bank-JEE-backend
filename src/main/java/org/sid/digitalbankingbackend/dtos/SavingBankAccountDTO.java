package org.sid.digitalbankingbackend.dtos;

import lombok.Data;
import org.sid.digitalbankingbackend.enums.AccountStatus;
import java.util.Date;



@Data
public class SavingBankAccountDTO extends BankAccountDTO{
    private String accountId;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customer;
    private double interestRate;

}


