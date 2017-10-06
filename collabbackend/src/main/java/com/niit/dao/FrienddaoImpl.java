package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;
@Repository
@Transactional
public class FrienddaoImpl implements Frienddao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<User> suggestedusers(String username) {
		Session session= sessionFactory.getCurrentSession();
		SQLQuery query=session.createSQLQuery("select * from user_details where username in (select username from user_details where username!=? minus (select fromid from friend_details where toid=? union select toid from friend_details where fromid=?) )");
		query.setString(0, username);
		query.setString(1, username);
		query.setString(2, username);
		query.addEntity(User.class);
		return query.list();
	}

	public void friendRequest(Friend friend) {
		Session session=sessionFactory.getCurrentSession();
		session.save(friend);//insert into friend 
	}
	public List<Friend> pendingRequests(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where toid=? and status='P'");
		query.setString(0, username);
		List<Friend> pendingRequests=query.list();
		return pendingRequests;
	}
	public void updatePendingRequest(Friend friend) {
		Session session=sessionFactory.getCurrentSession();
		if(friend.getStatus()=='A')
			session.update(friend);//update the status from P to A
		else
			session.delete(friend);//delete the record
		
	}
	public List<Friend> listOfFriends(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where status='A' and (fromid=? or toid=?)");
		query.setString(0, username);
		query.setString(1, username);
		return query.list();
	}

}
