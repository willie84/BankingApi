package com.example.BankingAPI.controller;

import com.example.BankingAPI.constants.ACTION;
import com.example.BankingAPI.constants.constants;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.LedgerAmount;
import com.example.BankingAPI.model.Transaction;
import com.example.BankingAPI.service.AccountService;
import com.example.BankingAPI.service.TransactionService;
import com.example.BankingAPI.utils.DepositDetails;
import com.example.BankingAPI.utils.QueryTransactionsListInput;
import com.example.BankingAPI.utils.TransactionDetails;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.BankingAPI.constants.constants.INSUFFICIENT_ACCOUNT_BALANCE;
import static com.example.BankingAPI.constants.constants.INVALID_SEARCH_CRITERIA;
import static com.example.BankingAPI.constants.constants.INVALID_TRANSACTION;
import static com.example.BankingAPI.constants.constants.NO_ACCOUNT_FOUND;
import static com.example.BankingAPI.constants.constants.SUCCESS;
import static com.example.BankingAPI.utils.createTransaction.createSingleTransaction;

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

    @GetMapping(value = "/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> queryTransactions(
            @Valid @RequestBody QueryTransactionsListInput queryTransactionsListInput) {
        if (InputValidator.isSearchTransactionQueryListValid(queryTransactionsListInput)) {
            Optional<List<Transaction>> transactionsList = transactionService.transactionsFortheAccount(queryTransactionsListInput.getAccountNumber());
            LOGGER.info("The transactions of this account have been retrieved");
            return new ResponseEntity<>(transactionsList, HttpStatus.OK);
        } else {
            LOGGER.error(INVALID_TRANSACTION);
            return new ResponseEntity<>(INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
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
            LOGGER.error(INVALID_TRANSACTION);
            return new ResponseEntity<>(INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdraw(
            @Valid @RequestBody DepositDetails withdrawDetails) {
        LOGGER.debug("Triggered AccountRestController.withdrawInput");

        // Validate input
        if (InputValidator.isAccountNoValid(withdrawDetails.getTargetAccountNo())) {
            // Attempt to retrieve the account information
            Account account = accountService.getAccount(
                    withdrawDetails.getTargetAccountNo());

            // Return the account details, or warn that no account was found for given input
            if (account == null) {
                return new ResponseEntity<>(NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                if (transactionService.isAmountAvailable(withdrawDetails.getAmount(), account.getCurrentBalance().getAmount())) {
                    transactionService.updateAccountBalance(account, withdrawDetails.getAmount(), ACTION.WITHDRAW);
                    LedgerAmount amount = new LedgerAmount("ZAR", withdrawDetails.getAmount());
                    Transaction transaction = createSingleTransaction(account,amount,transactionService.getTransactionRepository(),ACTION.WITHDRAW.toString());
                    LOGGER.info(constants.WITHDRAWAL_WAS_MADE_TO_THIS_ACCOUNT+" "+account);
                    return new ResponseEntity<>(transaction+" "+SUCCESS, HttpStatus.OK);
                }
                LOGGER.error(INSUFFICIENT_ACCOUNT_BALANCE + "The balance available is: " +account.getCurrentBalance());
                return new ResponseEntity<>(INSUFFICIENT_ACCOUNT_BALANCE + "The balance available is: " +account.getCurrentBalance(), HttpStatus.OK);
            }
        } else {
            LOGGER.error(INVALID_SEARCH_CRITERIA);
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
                LedgerAmount amount = new LedgerAmount("ZAR", depositDetails.getAmount());
                Transaction transaction = createSingleTransaction(account,amount,transactionService.getTransactionRepository(),ACTION.DEPOSIT.toString());
                LOGGER.info(constants.DEPOSIT_WAS_MADE_TO_THIS_ACCOUNT+" "+account);
                return new ResponseEntity<>(transaction+" "+SUCCESS, HttpStatus.OK);
            }
        } else {
            LOGGER.warn(constants.INVALID_SEARCH_CRITERIA);
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
