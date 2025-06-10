package com.springdemo.ebankingbackend.repositories;

import com.springdemo.ebankingbackend.entities.BankAccount;
import com.springdemo.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
