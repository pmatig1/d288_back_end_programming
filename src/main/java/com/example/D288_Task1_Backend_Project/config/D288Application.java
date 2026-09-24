package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;

@SpringBootApplication
public class D288Application {

    public static void main(String[] args) {
        SpringApplication.run(D288Application.class, args);
    }

    @Bean
    CommandLineRunner loadSampleCustomers(CustomerRepository customerRepository,
                                          DivisionRepository divisionRepository) {
        return args -> {
            if (customerRepository.count() >= 5) {
                return;
            }

            Division division = divisionRepository.findById(1L).orElse(null);
            if (division == null) {
                return;
            }

            if (customerRepository.count() < 1) customerRepository.save(customer("John", "Smith", "101 Main Street", "10001", "555-1001", division));
            if (customerRepository.count() < 2) customerRepository.save(customer("Jane", "Doe", "202 Oak Avenue", "10002", "555-1002", division));
            if (customerRepository.count() < 3) customerRepository.save(customer("Michael", "Johnson", "303 Pine Road", "10003", "555-1003", division));
            if (customerRepository.count() < 4) customerRepository.save(customer("Emily", "Brown", "404 Cedar Lane", "10004", "555-1004", division));
            if (customerRepository.count() < 5) customerRepository.save(customer("David", "Williams", "505 Maple Drive", "10005", "555-1005", division));
        };
    }

    private Customer customer(String first, String last, String address, String postal,
                              String phone, Division division) {
        Customer c = new Customer();
        c.setFirstName(first);
        c.setLastName(last);
        c.setAddress(address);
        c.setPostal_code(postal);
        c.setPhone(phone);
        c.setDivision(division);
        return c;
    }
}
