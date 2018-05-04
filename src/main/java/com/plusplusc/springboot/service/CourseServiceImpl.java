package com.plusplusc.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plusplusc.springboot.model.Course;
import com.plusplusc.springboot.repositories.CourseRepository;



@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseRepository courseRepository;

	public Course findById(Long id) {
		return courseRepository.findOne(id);
	}

	public Course findByTitle(String title) {
		return courseRepository.findByTitle(title);
	}

	public void saveCourse(Course course) {
		courseRepository.save(course);
	}

	public void updateCourse(Course course){
		saveCourse(course);
	}

	public void deleteCourseById(Long id){
		courseRepository.delete(id);
	}

	public void deleteAllCourses(){
		courseRepository.deleteAll();
    }
	
	public List<Course> findAllCourses(){
		return courseRepository.findAll();
	}

	public boolean isCourseExist(Course course) {
		return findByTitle(course.getTitle()) != null;
	}

}
