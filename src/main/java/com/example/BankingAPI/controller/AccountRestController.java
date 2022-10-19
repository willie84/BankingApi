package com.example.BankingAPI.controller;

import com.example.BankingAPI.constants.ACCOUNT_TYPE;
import com.example.BankingAPI.constants.constants;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.Customer;
import com.example.BankingAPI.service.AccountService;
import com.example.BankingAPI.service.CustomerService;
import com.example.BankingAPI.utils.AccountCreationDetails;
import com.example.BankingAPI.utils.GetAccountDetails;
import com.example.BankingAPI.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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

@RestController
@Validated
@RequestMapping("api/v1")
public class AccountRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRestController.class);

    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountRestController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAccount(
            @Valid @RequestBody GetAccountDetails getAccountDetails) {
        LOGGER.debug("Triggered AccountRestController creating an account");
        LOGGER.trace("A TRACE Message");
        LOGGER.debug("A DEBUG Message");
        LOGGER.info("An INFO Message");
        LOGGER.warn("A WARN Message");
        LOGGER.error("An ERROR Message");
        // Validate input
        if (InputValidator.isSearchCriteriaValid(getAccountDetails)) {
            // Attempt to retrieve the account information
            Account account = accountService.getAccount(getAccountDetails.getAccountNumber());
            // Return the account details, or warn that no account was found for given input
            if (account == null) {
                LOGGER.error(constants.NO_ACCOUNT_FOUND);
                return new ResponseEntity<>(constants.NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                LOGGER.info(constants.ACCOUNT_HAS_BEEN_FOUND);
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountCreationDetails accountCreationDetails) {
        LOGGER.debug("Triggered AccountRestController.createAccountInput");

        // Validate input
        if (InputValidator.isCreateAccountCriteriaValid(accountCreationDetails)) {
            // Attempt to retrieve the account information
            Optional<Customer> customer = customerService.getCustomer(accountCreationDetails.getIdNumber());

            if (customer.isEmpty()) {
                LOGGER.error(constants.NO_ACCOUNT_FOUND);
                return new ResponseEntity<>(constants.NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                List<Account> accountList = accountService.getAccount(customer.get(),accountCreationDetails.getBankName());
                if (!findIfAccountOfSameTypeExists(accountList, accountCreationDetails.getAccountType())) {
                    Account account = accountService.createAccount(accountCreationDetails.getBankName(), customer.get(),accountCreationDetails.getAccountType());
                    // Return the account details, or warn that no account was found for given input
                    if (account == null) {
                        LOGGER.error(constants.CREATE_ACCOUNT_FAILED);
                        return new ResponseEntity<>(constants.CREATE_ACCOUNT_FAILED, HttpStatus.OK);
                    } else {
                        LOGGER.trace(account+" Account was created successfully");
                        return new ResponseEntity<>(account, HttpStatus.OK);
                    }
                }
                else{
                    LOGGER.error(constants.DUPLICATE_ACCOUNT_TYPE);
                    return new ResponseEntity<>(constants.DUPLICATE_ACCOUNT_TYPE, HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            if(!ACCOUNT_TYPE.contains(accountCreationDetails.getAccountType())){
                LOGGER.error(constants.INVALID_ACCOUNT_TYPE);
                return new ResponseEntity<>(constants.INVALID_ACCOUNT_TYPE, HttpStatus.BAD_REQUEST);
            }
            LOGGER.error(constants.INVALID_SEARCH_CRITERIA);
            return new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }

    }

    public boolean findIfAccountOfSameTypeExists(List<Account> accountList, String accountType) {
        if (accountList.isEmpty()) {
            return false;
        } else {
            for (Account account1 : accountList) {
                if (account1.getAccountType().equals(accountType)) {
                    return true;
                }
            }
        }
        return false;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
