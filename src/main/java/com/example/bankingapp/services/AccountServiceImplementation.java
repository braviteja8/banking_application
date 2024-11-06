package com.example.bankingapp.services;

import com.example.bankingapp.dtos.AccountDto;
import com.example.bankingapp.exceptions.accountNotFoundException;
import com.example.bankingapp.models.Account;
import com.example.bankingapp.models.Transaction;
import com.example.bankingapp.repositories.AccountRepository;
import com.example.bankingapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("aci")
public class AccountServiceImplementation implements AccountServiceInterface{
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    public AccountServiceImplementation(AccountRepository accountRepository,TransactionRepository transactionRepository){
        this.accountRepository=accountRepository;
        this.transactionRepository=transactionRepository;
    }
//    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto){
//        Account account=new Account();
//        account.setId(accountDto.getId());
//        account.setAccountHolderName(accountDto.getAccountHolderName());
//        account.setBalance(accountDto.getBalance());
//        return new ResponseEntity<>(
//                accountService.createAccount(account), HttpStatus.CREATED);
//    }
    @Override
    public Account createAccount(Account account) {
        Account savedAccount=accountRepository.save(account);
        return account;
    }

    @Override
    public Account getAccount(Long id) throws accountNotFoundException {
        Optional<Account> accountOptional=accountRepository.findById(id);
        if(accountOptional.isEmpty()){
            throw new accountNotFoundException();
        }
        return accountOptional.get();
    }

    @Override
    public Account depositAmount(Long id, double amount) throws accountNotFoundException {
        Optional<Account>accountOptional=accountRepository.findById(id);
        if(accountOptional.isEmpty()){
            throw new accountNotFoundException();
        }
        Account account=accountOptional.get();
        account.setBalance(account.getBalance()+amount);
        Account savedAccount=accountRepository.save(account);
        Transaction transaction=new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSITED");
        transaction.setTimeStamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return savedAccount;
    }

    @Override
    public Account withdrawAmount(Long id, double amount) throws accountNotFoundException {
        Optional<Account>accountOptional=accountRepository.findById(id);
        if(accountOptional.isEmpty()){
            throw new accountNotFoundException();
        }
        Account account=accountOptional.get();
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient Amount");
        }
        account.setBalance(account.getBalance()-amount);
        Account savedAccount=accountRepository.save(account);
        Transaction transaction=new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimeStamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return savedAccount;
    }

    @Override
    public void transferFund(Long fromAccountId, Long toAccountId, double amount) throws accountNotFoundException {
        Optional<Account>accountOptional=accountRepository.findById(fromAccountId);
        if(accountOptional.isEmpty()){
            throw new accountNotFoundException();
        }
        Account fromAccount=accountOptional.get();

        Optional<Account>accountOptional1=accountRepository.findById(toAccountId);
        if(accountOptional1.isEmpty()){
            throw new accountNotFoundException();
        }
        Account toAccount=accountOptional1.get();
        if (fromAccount.getBalance()<amount) {
            throw new RuntimeException("Insufficient amount");
        }

        toAccount.setBalance(toAccount.getBalance()+amount);
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        accountRepository.save(toAccount);
        accountRepository.save(fromAccount);
        Transaction transaction=new Transaction();
        transaction.setAccountId(fromAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType("TRANSFER");
        transaction.setTimeStamp(LocalDateTime.now());
        transactionRepository.save(transaction);


    }

    @Override
    public List<Transaction> getAccountTransactions(Long id) {
        List<Transaction>transactions=transactionRepository.findByAccountIdOrderByTimeStampDesc(id);
        return transactions;
    }

}
