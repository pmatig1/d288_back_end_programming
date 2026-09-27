package com.example.D288_Task1_Backend_Project;

import com.example.D288_Task1_Backend_Project.dao.CustomerRepository;
import com.example.D288_Task1_Backend_Project.dao.DivisionRepository;
import com.example.D288_Task1_Backend_Project.entities.Customer;
import com.example.D288_Task1_Backend_Project.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import java.util.Date;
import java.util.List;

@SpringBootApplication
public class D288TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(D288TaskApplication.class, args);
    }

    @Bean
    CommandLineRunner loadSampleCustomers(CustomerRepository customerRepository,
                                          DivisionRepository divisionRepository) {
        return args -> {
            if (customerRepository.count() >= 5) {
                return;
            }

            Division division = divisionRepository.findAll().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "The supplied database must contain at least one division before sample customers can be created."));

            List<Customer> sampleCustomers = List.of(
                    createCustomer("John", "Smith", "101 Main Street", "10001", "555-1001", division),
                    createCustomer("Jane", "Doe", "202 Oak Avenue", "10002", "555-1002", division),
                    createCustomer("Michael", "Johnson", "303 Pine Road", "10003", "555-1003", division),
                    createCustomer("Emily", "Brown", "404 Cedar Lane", "10004", "555-1004", division),
                    createCustomer("David", "Williams", "505 Maple Drive", "10005", "555-1005", division)
            );

            long existing = customerRepository.count();
            if (existing < sampleCustomers.size()) {
                customerRepository.saveAll(sampleCustomers.subList((int) existing, sampleCustomers.size()));
            }
        };
    }

    private Customer createCustomer(String firstName, String lastName, String address,
                                    String postalCode, String phone, Division division) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setPostal_code(postalCode);
        customer.setPhone(phone);
        customer.setDivision(division);
        return customer;
    }
}