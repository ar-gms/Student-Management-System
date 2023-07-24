package com.example.myfirstrestapp;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Integer>{
    Optional<Student> findById(int id);

}
