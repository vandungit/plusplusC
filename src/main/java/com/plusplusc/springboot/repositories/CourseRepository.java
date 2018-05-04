package com.plusplusc.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plusplusc.springboot.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByTitle(String title);
}
