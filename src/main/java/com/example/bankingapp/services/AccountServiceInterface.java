package com.example.bankingapp.services;

import com.example.bankingapp.dtos.AccountDto;
import com.example.bankingapp.exceptions.accountNotFoundException;
import com.example.bankingapp.models.Account;
import com.example.bankingapp.models.Transaction;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


@Service
public interface AccountServiceInterface {
    Account createAccount(Account account);

    Account getAccount(Long id) throws accountNotFoundException;

    Account depositAmount(Long id, double amount) throws accountNotFoundException;

    Account withdrawAmount(Long id, double amount) throws accountNotFoundException;

    void transferFund(Long fromAccountId, Long toAccountId, double amount) throws accountNotFoundException;

    List<Transaction> getAccountTransactions(Long id);
}
