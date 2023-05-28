package org.sid.digitalbankingbackend.repositories;

import org.sid.digitalbankingbackend.entities.BankAccount;
import org.sid.digitalbankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findByCustomer_Id(Long id);
}
