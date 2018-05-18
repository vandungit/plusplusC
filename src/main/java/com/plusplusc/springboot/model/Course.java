package com.plusplusc.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="course")
public class Course implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name="title_course", nullable=false)
	private String title;

	@Column(name="content_course", nullable=false)
	private String content;

	@Column(name="image_course", nullable=false)
	private String image;

	@Column(name="video_course", nullable=false)
	private String video;
	
	@Column(name="price_course", nullable=false)
	private Long price;
	
	@Column(name="view_course", nullable=true)
	private Long view;
	
	@Column(name="postdate_course", nullable=true)
	private String postDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}

	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public Long getView() {
		return view;
	}
	public void setView(Long view) {
		this.view = view;
	}
	
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String post) {
		this.postDate = post;
	}

}