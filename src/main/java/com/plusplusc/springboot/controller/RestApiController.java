package com.plusplusc.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.plusplusc.springboot.model.Course;
import com.plusplusc.springboot.service.CourseService;
import com.plusplusc.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	CourseService courseService; //Service which will do all data retrieval/manipulation work
	Long view = (long) 1;

	// -------------------Retrieve All Courses---------------------------------------------

	@RequestMapping(value = "/course/", method = RequestMethod.GET)
	public ResponseEntity<List<Course>> listAlCourses() {
		List<Course> courses = courseService.findAllCourses();
		if (courses.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
	}

	// -------------------Retrieve Single Course------------------------------------------

	@RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCourse(@PathVariable("id") Long id) {
		logger.info("Fetching Course with id {}", id);
		Course course = courseService.findById(id);
		if (course == null) {
			logger.error("Course with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Course with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		course.setView(view);
		courseService.saveCourse(course);
		return new ResponseEntity<Course>(course, HttpStatus.OK);
	}

	// -------------------Create a Course-------------------------------------------

	@RequestMapping(value = "/course/", method = RequestMethod.POST)
	public ResponseEntity<?> createCourse(@RequestBody Course course, UriComponentsBuilder ucBuilder) {
		//logger.info("Creating Course : {}", course);

		if (courseService.isCourseExist(course)) {
			logger.error("Unable to create. A Course with name {} already exist", course.getTitle());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Course with title " + 
					course.getTitle() + " already exist."),HttpStatus.CONFLICT);
		}
		courseService.saveCourse(course);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/course/{id}").buildAndExpand(course.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	// ------------------- Update a Course ------------------------------------------------

	@RequestMapping(value = "/course/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
		logger.info("Updating Course with id {}", id);

		Course currentCourse = courseService.findById(id);

		if (currentCourse == null) {
			logger.error("Unable to update. Course with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Course with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentCourse.setTitle(course.getTitle());
		currentCourse.setContent(course.getContent());
		currentCourse.setImage(course.getImage());
		currentCourse.setVideo(course.getVideo());
		currentCourse.setView(course.getView());
		currentCourse.setPostDate(course.getPostDate());
		
		courseService.updateCourse(currentCourse);
		return new ResponseEntity<Course>(currentCourse, HttpStatus.OK);
	}

	// ------------------- Delete a Course-----------------------------------------

	@RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Course with id {}", id);

		Course course = courseService.findById(id);
		if (course == null) {
			logger.error("Unable to delete. Course with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Course with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		courseService.deleteCourseById(id);
		return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Courses-----------------------------

	@RequestMapping(value = "/course/", method = RequestMethod.DELETE)
	public ResponseEntity<Course> deleteAllCourses() {
		logger.info("Deleting All Courses");

		courseService.deleteAllCourses();
		return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
	}

}