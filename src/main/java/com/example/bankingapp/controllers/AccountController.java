package com.example.bankingapp.controllers;

import com.example.bankingapp.dtos.AccountDto;
import com.example.bankingapp.dtos.TransferFundDto;
import com.example.bankingapp.exceptions.accountNotFoundException;
import com.example.bankingapp.models.Account;
import com.example.bankingapp.models.Transaction;
import com.example.bankingapp.services.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {
    private AccountServiceInterface accountService;
    public AccountController(@Qualifier("aci") AccountServiceInterface accountService){
        this.accountService=accountService;
    }
    //Add account REST API
    @PostMapping()
    public Account createAccount(@RequestBody AccountDto accountDto){
        Account account=new Account();
        account.setId(accountDto.getId());
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setBalance(accountDto.getBalance());
        return accountService.createAccount(account);
    }
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable("id") Long id) throws accountNotFoundException {
        return accountService.getAccount(id);
    }

    @PostMapping ("/{id}/deposit")
    public Account depositAmount(@PathVariable("id") Long id,@RequestBody Map<String,Double> request) throws accountNotFoundException {
        return accountService.depositAmount(id,request.get("amount"));
    }
    @PostMapping("/{id}/withdraw")
    public Account withdrawAmount(@PathVariable("id")Long id,@RequestBody Map<String,Double>request
    ) throws accountNotFoundException {
        return accountService.withdrawAmount(id,request.get("amount"));
    }
    //do getall accs and delete acc


    @PostMapping("/transfer")
    public void transferFund(@RequestBody TransferFundDto transferFundDto) throws accountNotFoundException {
        accountService.transferFund(transferFundDto.getFromAccountId(), transferFundDto.getToAccountId(), transferFundDto.getAmount());
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getAccountTransactions(@PathVariable("id")Long id){
        return accountService.getAccountTransactions(id);
    }

}
