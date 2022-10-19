package com.example.BankingAPI.service;

import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.Customer;
import com.example.BankingAPI.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository=customerRepository;
    }

    public Optional<Customer>  getCustomer(String IdNumber) {
        Optional<Customer> customer = customerRepository
                .findByidNumber(IdNumber);
        System.out.println(customer);
        return customer;
    }

    public Customer createCustomer(String name, String IdNumber,String address) {
        Customer newCustomer = new Customer(name, address, IdNumber);
        return customerRepository.insert(newCustomer);
    }
}
