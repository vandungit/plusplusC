package com.plusplusc.springboot.service;


import java.util.List;

import com.plusplusc.springboot.model.Course;

public interface CourseService {
	
	Course findById(Long id);

	Course findByTitle(String title);

	void saveCourse(Course course);

	void updateCourse(Course course);

	void deleteCourseById(Long id);

	void deleteAllCourses();

	List<Course> findAllCourses();

	boolean isCourseExist(Course course);
}