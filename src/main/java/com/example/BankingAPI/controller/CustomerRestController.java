package com.example.BankingAPI.controller;

import com.example.BankingAPI.constants.constants;
import com.example.BankingAPI.model.Customer;
import com.example.BankingAPI.service.CustomerService;
import com.example.BankingAPI.utils.CustomerCreationDetails;
import com.example.BankingAPI.utils.GetCustomerDetails;
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
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("api/v1")
public class CustomerRestController {

   private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestController.class);

   private final CustomerService customerService;

   @Autowired
   public CustomerRestController(CustomerService customerService) {
      this.customerService = customerService;
   }

   @GetMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> checkCustomerExists(@Valid @RequestBody GetCustomerDetails getCustomerDetails) {
      LOGGER.debug("Triggered CustomerRestController.CheckCustomer");
      System.out.println(InputValidator.isCustomerSearchCriteriaValid(getCustomerDetails));
      // Validate input
      if (InputValidator.isCustomerSearchCriteriaValid(getCustomerDetails)) {
         // Attempt to retrieve the account information
         Optional<Customer> customer = customerService.getCustomer(getCustomerDetails.getidNumber());

         // Return the account details, or warn that no account was found for given input
         if (customer.isEmpty()) {
            LOGGER.error((String) constants.NO_CUSTOMER_FOUND);
            return new ResponseEntity<>(constants.NO_CUSTOMER_FOUND, HttpStatus.OK);
         } else {
            LOGGER.info(customer.toString() + "Was created");
            return new ResponseEntity<>(customer, HttpStatus.OK);
         }
      } else {
         LOGGER.error(constants.INVALID_SEARCH_CRITERIA);
         return new ResponseEntity<>(constants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
      }
   }

   @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerCreationDetails customerCreationDetails) {
      LOGGER.debug("Triggered CustomerRestController.createCustomer");

      // Validate input
      if (InputValidator.isCreateCustomerCriteriaValid(customerCreationDetails)) {
         // Attempt to retrieve the customer information
         Optional<Customer> customerz = customerService.getCustomer(customerCreationDetails.getIdNumber());

         if(customerz.isEmpty()){
         Customer customer =
               customerService.createCustomer(
                     customerCreationDetails.getName(),
                     customerCreationDetails.getIdNumber(),
                     customerCreationDetails.getAddress());
         // Return the account details, or warn that no account was found for given input
         if (customer == null) {
            return new ResponseEntity<>(constants.CREATE_CUSTOMER_FAILED, HttpStatus.OK);
         } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
         }
      }
         else{
           LOGGER.error((String) constants.CUSTOMER_ALREADY_EXISTS_WITH_SAME_ID_NUMBER);
            return new ResponseEntity<>(constants.CUSTOMER_ALREADY_EXISTS_WITH_SAME_ID_NUMBER, HttpStatus.OK);
         }
      }

      if(!customerCreationDetails.getIdNumber().isBlank() && !InputValidator.isCustomerIDRightlength(customerCreationDetails.getIdNumber())){
         LOGGER.error((String) constants.INVALID_ID_LENGTH);
         return new ResponseEntity<>(constants.INVALID_ID_LENGTH, HttpStatus.BAD_REQUEST);
      }
      else {
         LOGGER.error((String) constants.INVALID_SEARCH_CRITERIA);
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
