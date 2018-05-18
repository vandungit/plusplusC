package com.plusplusc.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plusplusc.springboot.model.Comment;
import com.plusplusc.springboot.model.Course;
import com.plusplusc.springboot.repositories.CommentRepository;



@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;

	public Comment findById(Long id) {
		return commentRepository.findOne(id);
	}

	public Comment findByName(String name) {
		return commentRepository.findByName(name);
	}

	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	/*public void updateComment(Comment comment) {
		saveComment(comment);	
	}*/
	
	public void deleteCommentById(Long id){
		commentRepository.delete(id);
	}

	public void deleteAllComments(){
		commentRepository.deleteAll();
    }
	
	public List<Comment> findAllComments(){
		return commentRepository.findAll();
	}

	public boolean isCommentExist(Comment comment) {
		return findByName(comment.getName()) != null;
	}


}
