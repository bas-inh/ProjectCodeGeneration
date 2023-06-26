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

        if (transactionValidatorService.checkCurrentOrSavings(accountService.findAccountByIban(trans.getFrom().getIban()), accountService.findAccountByIban(trans.getTo()))) {

            if (!transactionValidatorService.isUserOwner(accountService.findAccountByIban(trans.getFrom().getIban()), accountService.findAccountByIban(trans.getTo()))) {

                throw new IllegalArgumentException("Cannot create transaction; cannot transfer from or to savings account that does not belong to you.");

            } else {
                checkGeneralConditions(trans); // Check if all conditions are met:
            }
        } else {
            checkGeneralConditions(trans); // Check if all conditions are met
        }

        updateFromBalance(trans);
        updateToBalance(trans);

        return transactionRepo.save(trans);
    }



    private void checkGeneralConditions(Transaction trans) {

        if (!transactionValidatorService.checkNotSameAccount(trans.getFrom().getIban(), trans.getTo())) {

            throw new IllegalArgumentException("Cannot create transaction; cannot transfer between two accounts that are the same.");

        } else {

            if (!transactionValidatorService.checkActive(trans.getFrom().getIban(), trans.getTo())) {

                throw new IllegalArgumentException("Cannot create transaction; at least one of the accounts is inactive.");

            } else {

                if (trans.getAmount() <= 0) {

                    throw new IllegalArgumentException("Cannot create transaction; Amount must be greater than 0.");

                } else {

                    Account account = accountService.findAccountByIban(trans.getFrom().getIban());

                    if (!transactionValidatorService.checkAbsLimit(account, trans.getAmount())) {

                        throw new IllegalArgumentException("Cannot create transaction; Cannot exceed absolute limit " + "(" + account.getAbsLimit() + ").");

                    } else {

                        User userPerforming = userService.findById(trans.getUserPerforming());

                        if (!transactionValidatorService.checkDayLimit(userPerforming, trans)) {

                            throw new IllegalArgumentException("Cannot create transaction; Cannot exceed day limit " + "(" + userPerforming.getDayLimit() + ").");

                        }
                    }
                }
            }
        }
    }


    private void updateFromBalance(Transaction trans) {
        Account account = accountService.findAccountByIban(trans.getFrom().getIban());

        account.setBalance((account.getBalance() - trans.getAmount()));
        accountRepo.save(account);
    }



    private void updateToBalance(Transaction trans) {
        Account account = accountService.findAccountByIban(trans.getTo());

        account.setBalance((account.getBalance() + trans.getAmount()));
        accountRepo.save(account);
    }

    public Transaction createWithdrawal(Transaction trans) {
        checkPinCode(trans);
        checkGeneralConditions(trans);

        updateFromBalance(trans);

        return transactionRepo.save(trans);
    }

    public Transaction createDeposit(Transaction trans) {

        Integer pinCode = trans.getPinCode();

        if (pinCode == null) {
            throw new IllegalArgumentException("Deposit failed; no pincode entered");
        }

        if (!pinCode.equals(accountService.findAccountByIban(trans.getTo()).getPinCode())) {
            throw new IllegalArgumentException("Deposit failed; wrong pin code entered");
        }

        updateToBalance(trans);

        return transactionRepo.save(trans);
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
        if (isPinCodeNull(trans.getPinCode())) {
            throw new IllegalArgumentException("Withdrawal failed; no pin code entered");
        }
        if (!isPinCodeCorrect(trans)) {
            throw new IllegalArgumentException("Withdrawal failed; wrong pin code entered");
        }
    }
}
