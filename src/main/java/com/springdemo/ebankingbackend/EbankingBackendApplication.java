package com.springdemo.ebankingbackend;

import com.springdemo.ebankingbackend.dtos.BankAccountDTO;
import com.springdemo.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.springdemo.ebankingbackend.dtos.CustomerDTO;
import com.springdemo.ebankingbackend.dtos.SavingBankAccountDTO;
import com.springdemo.ebankingbackend.entities.*;
import com.springdemo.ebankingbackend.enums.AccountStatus;
import com.springdemo.ebankingbackend.enums.OperationType;
import com.springdemo.ebankingbackend.repositories.AccountOperationRepository;
import com.springdemo.ebankingbackend.repositories.BankAccountRepository;
import com.springdemo.ebankingbackend.repositories.CustomerRepository;
import com.springdemo.ebankingbackend.services.BankAccountService;
import com.springdemo.ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, BankService bankService){
        return args -> {
            Stream.of("reda", "adam", "youssef", "salma").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 90000, 5.5, customer.getId());
                    List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
                    for (BankAccountDTO bankAccount : bankAccounts) {
                        for(int i = 0; i < 10; i++) {
                            String accountId;
                            if(bankAccount instanceof SavingBankAccountDTO){
                                accountId = ((SavingBankAccountDTO) bankAccount).getId();
                            } else {
                                accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                            }
                            bankAccountService.credit(accountId,10000+Math.random() * 12000, "Credit Operation");
                            bankAccountService.debit(accountId,1000+Math.random() * 9000, "Debit Operation");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error creating current account for customer " + customer.getName() + ": " + e.getMessage());
                     ;
                }

            });
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("reda", "adam", "youssef", "salma").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc -> {
                for(int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });

        };
    }
}
