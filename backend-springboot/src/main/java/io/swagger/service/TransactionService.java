package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repo.AccountRepo;
import io.swagger.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

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

        // User performing = owner account from

        // Check if one of the accounts is of type savings
        // If at least one account is a savings account continue savings transaction
        if (transactionValidatorService.checkCurrentOrSavings(accountService.findAccountByIban(trans.getFrom().getIban()), accountService.findAccountByIban(trans.getTo()))) {
            // At least one account is a savings account

            // Check if user is owner of both accounts
            if (!transactionValidatorService.isUserOwner(accountService.findAccountByIban(trans.getFrom().getIban()), accountService.findAccountByIban(trans.getTo()))) {
                // User is not the owner of both accounts
                throw new IllegalArgumentException("Cannot create transaction; cannot transfer from or to savings account that does not belong to you.");
            } else {
                // Account from and account to might both be of type savings
                checkGeneralConditions(trans); // Check if all conditions are met:
            }
        } else { // Do transaction between two current accounts
            checkGeneralConditions(trans); // Check if all conditions are met
        }
        // Update balance account from
        updateFromBalance(trans);
        // Update balance account to
        updateToBalance(trans);
        // Commit and save transaction
        return transactionRepo.save(trans);
    }

    public Transaction createWithdrawal(Transaction trans) {
        checkPinCode(trans);
        checkGeneralConditions(trans);
        // Update balance account from (Owner is user)
        updateFromBalance(trans);
        // Account to Owner is bank
        // Commit and save transaction
        return transactionRepo.save(trans);
    }

    public Transaction createDeposit(Transaction trans) {

        // Get pin code (given by user) from transaction
        Integer pinCode = trans.getPinCode();

        // Check if pin code is filled in
        if (pinCode == null) {
            throw new IllegalArgumentException("Deposit failed; no pincode entered");
        }

        // Check if the given pin code matches the account pin code
        if (!pinCode.equals(accountService.findAccountByIban(trans.getTo()).getPinCode())) {
            throw new IllegalArgumentException("Deposit failed; wrong pin code entered");
        }
        // Account from Owner is bank
        // Update balance account to (Owner is user)
        updateToBalance(trans);
        // Commit and save transaction
        return transactionRepo.save(trans);
    }

    private void checkGeneralConditions(Transaction trans) {
        // Check if account from and account to are not the same account
        if (!transactionValidatorService.checkNotSameAccount(trans.getFrom().getIban(), trans.getTo())) {
            // Account from is same as account to
            throw new IllegalArgumentException("Cannot create transaction; cannot transfer between two accounts that are the same.");
        } else {
            // Check if both accounts are active
            if (!transactionValidatorService.checkActive(trans.getFrom().getIban(), trans.getTo())) {
                throw new IllegalArgumentException("Cannot create transaction; at least one of the accounts is inactive.");
            } else {
                // Check if given transaction amount is greater than 0
                if (trans.getAmount() <= 0) {
                    throw new IllegalArgumentException("Cannot create transaction; Amount must be greater than 0.");
                } else {
                    // Check if absolute limit is exceeded
                    Account account = accountService.findAccountByIban(trans.getFrom().getIban());
                    if (!transactionValidatorService.checkAbsLimit(account, trans.getAmount())) {
                        throw new IllegalArgumentException("Cannot create transaction; Cannot exceed absolute limit " + "(" + account.getAbsLimit() + ").");
                    } else {
                        // Check if day limit is exceeded
                        User userPerforming = userService.findById(trans.getUserPerforming());
                        if (!transactionValidatorService.checkDayLimit(userPerforming, trans)) {
                            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed day limit " + "(" + userPerforming.getDayLimit() + ").");
                        }
                    }
                }
            }
        }
    }

    // Update account from
    private void updateFromBalance(Transaction trans) {
        Account account = accountService.findAccountByIban(trans.getFrom().getIban());

        // Given that account exists
        account.setBalance((account.getBalance() - trans.getAmount()));
        accountRepo.save(account);
    }

    // Update account to
    private void updateToBalance(Transaction trans) {
        Account account = accountService.findAccountByIban(trans.getTo());

        // Given that account exists
        account.setBalance((account.getBalance() + trans.getAmount()));
        accountRepo.save(account);
    }

    public Transaction findTransactionById(UUID id) {
        return transactionRepo.findTransactionById(id);
    }

    public void deleteTransaction(UUID id) {
        transactionRepo.delete(findTransactionById(id));
    }

    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getTransactionsFromToday(LocalDate timeStamp) {
        return transactionRepo.findAllByTimestamp(timeStamp);
    }

    private boolean isPinCodeNull(Integer pinCode) {
        // Check if pin code is filled in
        return pinCode == null;
    }

    private boolean isPinCodeCorrect(Transaction trans) {
        // Check if pin code is correct
        return trans.getPinCode().equals(accountService.findAccountByIban(trans.getFrom().getIban()).getPinCode());
    }

    private void checkPinCode(Transaction trans) {
        // Get pin code (given by user) from transaction
        if (isPinCodeNull(trans.getPinCode())) {
            throw new IllegalArgumentException("Withdrawal failed; no pin code entered");
        }
        // Check if the given pin code matches the account pin code
        if (!isPinCodeCorrect(trans)) {
            throw new IllegalArgumentException("Withdrawal failed; wrong pin code entered");
        }
    }
}
