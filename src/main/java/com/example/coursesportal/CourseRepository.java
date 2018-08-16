package com.example.coursesportal;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseRepository extends CrudRepository<Course, Long> {
    ArrayList<Course> findByUsername(String username);
}
