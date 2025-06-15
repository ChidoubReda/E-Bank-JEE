package com.springdemo.ebankingbackend.web;

import com.springdemo.ebankingbackend.dtos.AccountHistoryDTO;
import com.springdemo.ebankingbackend.dtos.AccountOperationDTO;
import com.springdemo.ebankingbackend.dtos.BankAccountDTO;
import com.springdemo.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;


    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }


    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                        @RequestParam(name="page",defaultValue = "0") int page ,
                                        @RequestParam(name="size",defaultValue = "5") int size) {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }
}
