package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.model.enumeration.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionValidatorService {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;


    // Method to check if it is current or savings account

    public boolean checkCurrentOrSavings(Account accountFrom, Account accountTo) {
        return isAccountSavings(accountFrom, accountTo);
    }
    // Method to check if the user is the owner of the account

    public boolean isUserOwner(Account from, Account to) {
        return isUserOwnerOfAccount(from, to);
    }

    // Method to check if the from account is not the same as to account

    public boolean checkNotSameAccount(String accountFrom, String accountTo) {
        return isFromSameAsTo(accountFrom, accountTo);
    }

    // Method to check if there is sufficient funds (absolute limit)

    public boolean checkAbsLimit(Account accountFrom, double amount) {
        return isAbsoluteLimitExceeded(accountFrom.getBalance(), amount, accountFrom.getAbsLimit());
    }

    // Method to check if it does not override day limit

    public boolean checkDayLimit(User user, Transaction trans) {
        return isDayLimitExceeded(user.getDayLimit(), trans);
    }

    // Method to check if both accounts are active

    public boolean checkActive(String ibanFrom, String ibanTo) {
        return areAccountsActive(accountService.findAccountByIban(ibanFrom), accountService.findAccountByIban(ibanTo));
    }

    private boolean isAccountSavings(Account from, Account to) {
        return from.getAccountType().equals(AccountType.SAVINGS) || to.getAccountType().equals(AccountType.SAVINGS);
    }

    private boolean isUserOwnerOfAccount(Account from, Account to) {
        return from.getUser().getId().equals(to.getUser().getId());
    }

    private boolean isFromSameAsTo(String from, String to) {
        return !from.equals(to);
    }

    private boolean isAbsoluteLimitExceeded(double balance, double amount, double absLimit) {
        return (balance - amount) > absLimit;
    }

    private boolean isDayLimitExceeded(Double dayLimit, Transaction trans) {
        List<Transaction> transToday = transactionService.getTransactionsFromToday(LocalDate.now());
        double total = 0;
        for (Transaction transaction : transToday) {
            total += transaction.getAmount();
        }

        // Add the amount of the new transaction to the total
        total += trans.getAmount();
        return total <= dayLimit;
    }

    private boolean areAccountsActive(Account from, Account to) {
        return from.getActive() && to.getActive();
    }
}
