package com.example.BankingAPI.controller;

import com.example.BankingAPI.constants.ACTION;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.service.AccountService;
import com.example.BankingAPI.service.TransactionService;
import com.example.BankingAPI.utils.DepositDetails;
import com.example.BankingAPI.utils.TransactionDetails;
import com.example.BankingAPI.utils.WithdrawDetails;
import com.example.BankingAPI.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.example.BankingAPI.constants.constants.INSUFFICIENT_ACCOUNT_BALANCE;
import static com.example.BankingAPI.constants.constants.INVALID_SEARCH_CRITERIA;
import static com.example.BankingAPI.constants.constants.INVALID_TRANSACTION;
import static com.example.BankingAPI.constants.constants.NO_ACCOUNT_FOUND;
import static com.example.BankingAPI.constants.constants.SUCCESS;

@RestController
@RequestMapping("api/v1")
public class TransactionRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRestController.class);

    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionRestController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/transfer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeTransfer(
            @Valid @RequestBody TransactionDetails transactionDetails) {
        if (InputValidator.isSearchTransactionValid(transactionDetails)) {
//            new Thread(() -> transactionService.makeTransfer(transactionInput));
            boolean isComplete = transactionService.makeTransfer(transactionDetails);
            LOGGER.info("Transfer was done succesfully");
            return new ResponseEntity<>(isComplete, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdraw(
            @Valid @RequestBody WithdrawDetails withdrawDetails) {
        LOGGER.debug("Triggered AccountRestController.withdrawInput");

        // Validate input
        if (InputValidator.isSearchCriteriaValid(withdrawDetails)) {
            // Attempt to retrieve the account information
            Account account = accountService.getAccount(
                    withdrawDetails.getAccountNumber());

            // Return the account details, or warn that no account was found for given input
            if (account == null) {
                return new ResponseEntity<>(NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                if (transactionService.isAmountAvailable(withdrawDetails.getAmount(), account.getCurrentBalance().getAmount())) {
                    transactionService.updateAccountBalance(account, withdrawDetails.getAmount(), ACTION.WITHDRAW);
                    return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                }
                return new ResponseEntity<>(INSUFFICIENT_ACCOUNT_BALANCE, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deposit(
            @Valid @RequestBody DepositDetails depositDetails) {
        LOGGER.debug("Triggered AccountRestController.depositInput");

        // Validate input
        if (InputValidator.isAccountNoValid(depositDetails.getTargetAccountNo())) {
            // Attempt to retrieve the account information
            Account account = accountService.getAccount(depositDetails.getTargetAccountNo());

            // Return the account details, or warn that no account was found for given input
            if (account == null) {
                return new ResponseEntity<>(NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                transactionService.updateAccountBalance(account, depositDetails.getAmount(), ACTION.DEPOSIT);
                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
