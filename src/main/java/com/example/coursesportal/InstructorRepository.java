package com.example.coursesportal;

import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    public Instructor findById(long name);
}
