package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repo.AccountRepo;
import io.swagger.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    TransactionValidatorService transactionValidatorService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    UserService userService;

    public Transaction createTransaction(Transaction trans) {
        Account accountFrom = getAccountByIban(trans.getFrom().getIban());
        Account accountTo = getAccountByIban(trans.getTo());

        if (!transactionValidatorService.areBothAccountsSavings(accountFrom, accountTo)) {
            checkGeneralConditions(trans);
            updateFromBalance(accountFrom, trans.getAmount());
            updateToBalance(accountTo, trans.getAmount());
        } else {
            throw new IllegalArgumentException("Cannot transfer between two savings accounts.");
        }

        return transactionRepo.save(trans);
    }

    private void checkGeneralConditions(Transaction trans) {
        String fromIban = trans.getFrom().getIban();
        String toIban = trans.getTo();

        if (!transactionValidatorService.isDifferentAccount(fromIban, toIban)) {
            throw new IllegalArgumentException("Cannot create transaction; cannot transfer between two accounts that are the same.");
        }

        if (!transactionValidatorService.areAccountsActive(fromIban, toIban)) {
            throw new IllegalArgumentException("Cannot create transaction; at least one of the accounts is inactive.");
        }

        if (trans.getAmount() <= 0) {
            throw new IllegalArgumentException("Cannot create transaction; Amount must be greater than 0.");
        }

        Optional<Account> accountFrom = accountService.findAccountByIban(fromIban);
        if (!transactionValidatorService.hasSufficientFunds(accountFrom, trans.getAmount())) {
            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed absolute limit.");
        }

        User userPerforming = userService.findById(trans.getUserPerforming());
        if (!transactionValidatorService.doesNotExceedDayLimit(userPerforming, trans)) {
            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed day limit.");
        }
    }

    private void updateToBalance(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
    }

    private void updateFromBalance(Account account, double amount) {
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
    }

    public Transaction createWithdrawal(Transaction trans) {
        validatePinCode(trans);
        checkGeneralConditions(trans);
        Account accountFrom = getAccountByIban(trans.getFrom().getIban());
        updateFromBalance(accountFrom, trans.getAmount());
        return transactionRepo.save(trans);
    }

    public Transaction createDeposit(Transaction trans) {
        validatePinCode(trans);
        Account accountTo = getAccountByIban(trans.getTo());
        updateToBalance(accountTo, trans.getAmount());
        return transactionRepo.save(trans);
    }

    public Transaction findTransactionById(UUID id) {
        return transactionRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction with the given ID not found."));
    }

    public List<Transaction> findTransactionsByUserId(UUID userId) {
        return transactionRepo.findByUserPerforming(userId);
    }

    private boolean isPinCodeNull(Integer pinCode) {
        return pinCode == null;
    }

    private boolean isPinCodeCorrect(Transaction trans) {
        Account account = getAccountByIban(trans.getFrom().getIban());
        return trans.getPinCode().equals(account.getPinCode());
    }

    private void validatePinCode(Transaction trans) {
        if (isPinCodeNull(trans.getPinCode())) {
            throw new IllegalArgumentException("Operation failed; no pin code entered");
        }
        if (!isPinCodeCorrect(trans)) {
            throw new IllegalArgumentException("Operation failed; wrong pin code entered");
        }
    }

    private Account getAccountByIban(String iban) {
        return accountService.findAccountByIban(iban).orElseThrow(() -> new NoSuchElementException("Account with the given IBAN not found."));
    }
}
