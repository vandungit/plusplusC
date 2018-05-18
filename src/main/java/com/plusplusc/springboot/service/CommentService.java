package com.plusplusc.springboot.service;

import java.util.List;

import com.plusplusc.springboot.model.Comment;

public interface CommentService {
	
	Comment findById(Long id);

	Comment findByName(String name);

	void saveComment(Comment comment);
	
	//void updateComment(Comment comment);
	
	void deleteCommentById(Long id);

	void deleteAllComments();

	List<Comment> findAllComments();

	boolean isCommentExist(Comment comment);
}