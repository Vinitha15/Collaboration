package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="blogpost_details")
public class BlogPost {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int blogpostId;
	private String blogtitle;
	@Lob
	private String blogdescription;
	private Date postedOn;
	@ManyToOne
	private User postedby;
	private boolean approved;
	private String rejectionReason;
	private boolean viewedStatus;
	
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public boolean isViewedStatus() {
		return viewedStatus;
	}
	public void setViewedStatus(boolean viewedStatus) {
		this.viewedStatus = viewedStatus;
	}
	public int getBlogpostId() {
		return blogpostId;
	}
	public void setBlogpostId(int blogpostId) {
		this.blogpostId = blogpostId;
	}
	public String getBlogtitle() {
		return blogtitle;
	}
	public void setBlogtitle(String blogtitle) {
		this.blogtitle = blogtitle;
	}
	public String getBlogdescription() {
		return blogdescription;
	}
	public void setBlogdescription(String blogdescription) {
		this.blogdescription = blogdescription;
	}
	public Date getPostedOn() {
		return postedOn;
	}
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	public User getPostedby() {
		return postedby;
	}
	public void setPostedby(User postedby) {
		this.postedby = postedby;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
}
