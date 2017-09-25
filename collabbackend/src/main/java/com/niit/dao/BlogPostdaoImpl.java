package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		Query query=session.createQuery("from BlogPost where approved="+approved);
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

}
