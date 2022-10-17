package com.example.BankingAPI.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.example.BankingAPI.utils.GetAccountDetails;
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

import com.example.BankingAPI.constants.constants;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.Customer;
import com.example.BankingAPI.service.AccountService;
import com.example.BankingAPI.service.CustomerService;
import com.example.BankingAPI.utils.AccountCreationDetails;
import com.example.BankingAPI.validators.InputValidator;

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
      LOGGER.debug("Triggered AccountRestController.accountInput");

      // Validate input
      if (InputValidator.isSearchCriteriaValid(getAccountDetails)) {
         // Attempt to retrieve the account information
         Account account = accountService.getAccount(getAccountDetails.getAccountNumber());

         // Return the account details, or warn that no account was found for given input
         if (account == null) {
            return new ResponseEntity<>(constants.NO_ACCOUNT_FOUND, HttpStatus.OK);
         } else {
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
         Customer customer = customerService.getCustomer(accountCreationDetails.getIdNumber());

         if (customer == null) {
            return new ResponseEntity<>(constants.NO_ACCOUNT_FOUND, HttpStatus.OK);
         } else {
            Account account = accountService.createAccount(accountCreationDetails.getBankName(), customer);

            // Return the account details, or warn that no account was found for given input
            if (account == null) {
               return new ResponseEntity<>(constants.CREATE_ACCOUNT_FAILED, HttpStatus.OK);
            } else {
               return new ResponseEntity<>(account, HttpStatus.OK);
            }
         }
      } else {
         return new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
      }

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
