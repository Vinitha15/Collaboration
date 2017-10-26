package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
@Repository
@Transactional
public class BlogPostdaoImpl implements BlogPostdao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveblogpost(BlogPost blogpost) {
		Session session=sessionFactory.getCurrentSession();
		session.save(blogpost);
		
	}

	public List<BlogPost> getallblogposts(int approved) {
		Session session=sessionFactory.getCurrentSession();
		Query query=null;
		if(approved==1)
			query=session.createQuery("from BlogPost where approved="+approved);
		else
			query=session.createQuery("from BlogPost where approved=0 and rejectionReason is null");
		List<BlogPost> blogposts=query.list();
		return blogposts;
	}

	public BlogPost getblogpostbyid(int id) {
		Session session=sessionFactory.getCurrentSession();
		return (BlogPost) session.get(BlogPost.class, id);
	}

	public void updateblogpost(BlogPost blogpost) {
		Session session=sessionFactory.getCurrentSession();
		session.update(blogpost);
		
	}

	public List<BlogPost> getapprovedblogposts(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where postedby.username=? and viewedStatus=? and (approved=1 or rejectionReason!=null)");
		query.setString(0, username);
		query.setBoolean(1, false);
		List<BlogPost> blogposts=query.list();
		return blogposts;
	}

	public void saveblogcomment(BlogComment blogcomment) {
		Session session=sessionFactory.getCurrentSession();
		session.save(blogcomment);
	}

	public List<BlogComment> getallcomments(int blogid) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogComment where blogpost.blogpostId=?");
		query.setInteger(0,blogid);
		return query.list();
	}

	public List<BlogPost> getablogpostsbyuserid(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where postedby.username=? and approved=1");
		query.setString(0,username);
		return query.list();
	}

}
