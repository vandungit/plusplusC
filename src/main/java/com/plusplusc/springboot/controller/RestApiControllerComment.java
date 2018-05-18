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

import com.plusplusc.springboot.model.Comment;
import com.plusplusc.springboot.service.CommentService;
import com.plusplusc.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiControllerComment {

	public static final Logger logger = LoggerFactory.getLogger(RestApiControllerCourse.class);

	@Autowired
	CommentService commentService; //Service which will do all data retrieval/manipulation work
	
	
	// -------------------Retrieve All Comments---------------------------------------------

		@RequestMapping(value = "/comment/", method = RequestMethod.GET)
		public ResponseEntity<List<Comment>> listAllComments() {
			List<Comment> comments = commentService.findAllComments();
			if (comments.isEmpty()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
				// You many decide to return HttpStatus.NOT_FOUND
			}
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		}

	// -------------------Retrieve Single Comment------------------------------------------

		@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getComment(@PathVariable("id") Long id) {
			logger.info("Fetching Comment with id {}", id);
			Comment comment = commentService.findById(id);
			if (comment == null) {
				logger.error("Comment with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Comment with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Comment>(comment, HttpStatus.OK);
		}
	
	// -------------------Create a Comment-------------------------------------------

		@RequestMapping(value = "/comment/", method = RequestMethod.POST)
		public ResponseEntity<?> createComment(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
			//logger.info("Creating Comment : {}", comment);
			commentService.saveComment(comment);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/course/comment/{id}").buildAndExpand(comment.getId()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}
		

	// ------------------- Delete a Comment-----------------------------------------

		@RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteComment(@PathVariable("id") long id) {
			logger.info("Fetching & Deleting Comment with id {}", id);

			Comment comment = commentService.findById(id);
			if (comment == null) {
				logger.error("Unable to delete. Comment with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Unable to delete. Comment with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}
			commentService.deleteCommentById(id);
			return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
		}
		

	// ------------------- Delete All Comments-----------------------------

		@RequestMapping(value = "/comment/", method = RequestMethod.DELETE)
		public ResponseEntity<Comment> deleteAllComments() {
			logger.info("Deleting All Comments");

			commentService.deleteAllComments();
			return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
		}
}