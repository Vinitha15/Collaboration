package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="blogcomment_details")
public class BlogComment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name="commented_by")
	private User commentedby;
	private Date commentedOn;
	private String commenttxt;
	@ManyToOne
	@JoinColumn(name="blogpost_id")
	private BlogPost blogpost;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getCommentedby() {
		return commentedby;
	}
	public void setCommentedby(User commentedby) {
		this.commentedby = commentedby;
	}
	public Date getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}
	public String getCommenttxt() {
		return commenttxt;
	}
	public void setCommenttxt(String commenttxt) {
		this.commenttxt = commenttxt;
	}
	public BlogPost getBlogpost() {
		return blogpost;
	}
	public void setBlogpost(BlogPost blogpost) {
		this.blogpost = blogpost;
	}
	
}
