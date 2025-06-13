package com.springdemo.ebankingbackend.services;

import com.springdemo.ebankingbackend.entities.BankAccount;
import com.springdemo.ebankingbackend.entities.CurrentAccount;
import com.springdemo.ebankingbackend.entities.SavingAccount;
import com.springdemo.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter (){
        BankAccount bankAccount =
                bankAccountRepository.findById("09169567-e63f-4225-a3b4-935405e3d3ea").orElse(null);
        if(bankAccount != null) {
            System.out.println("Account ID: " + bankAccount.getId());
            System.out.println("Account Balance: " + bankAccount.getBalance());
            System.out.println("Account Created At: " + bankAccount.getCreatedAt());
            System.out.println("Account Status: " + bankAccount.getStatus());
            System.out.println("Account Customer: " + bankAccount.getCustomer().getName());
            System.out.println("Account Type: " + bankAccount.getClass().getSimpleName());
            if(bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft: " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if(bankAccount instanceof SavingAccount) {
                System.out.println("Rate:" + ((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println("============================================");
                System.out.println("Operation Type: " + op.getType());
                System.out.println("Operation Amount: " + op.getAmount());
                System.out.println("Operation Date: " + op.getOperationDate());
            });
        }

    }
}
