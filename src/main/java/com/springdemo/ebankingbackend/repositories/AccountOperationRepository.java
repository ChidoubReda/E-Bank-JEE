package com.springdemo.ebankingbackend.repositories;

import com.springdemo.ebankingbackend.entities.AccountOperation;
import com.springdemo.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
