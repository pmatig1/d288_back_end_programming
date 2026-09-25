package com.example.D288_Task1_Backend_Project.config;

import com.example.D288_Task1_Backend_Project.entities.Cart;
import com.example.D288_Task1_Backend_Project.entities.CartItem;
import com.example.D288_Task1_Backend_Project.entities.Country;
import com.example.D288_Task1_Backend_Project.entities.Customer;
import com.example.D288_Task1_Backend_Project.entities.Division;
import com.example.D288_Task1_Backend_Project.entities.Excursion;
import com.example.D288_Task1_Backend_Project.entities.Vacation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(
                Country.class,
                Division.class,
                Customer.class,
                Cart.class,
                CartItem.class,
                Vacation.class,
                Excursion.class
        );

        cors.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}