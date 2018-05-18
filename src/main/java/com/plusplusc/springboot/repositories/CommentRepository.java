package com.plusplusc.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plusplusc.springboot.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByName(String name);
}
