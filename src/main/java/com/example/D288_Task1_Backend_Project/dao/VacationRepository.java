package com.example.D288_Task1_Backend_Project.dao;

import com.example.D288_Task1_Backend_Project.entities.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource
public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
