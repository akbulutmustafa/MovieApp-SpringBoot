package com.moviePage.movieP.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviePage.movieP.Entities.User;

@Repository
public class HibernateUserDal implements IUserDal{
	
	private EntityManager entityManager;
	
	@Autowired
	public HibernateUserDal(EntityManager entityManager) {
		this.entityManager=entityManager;
	}

	@Override
	@Transactional
	public List<User> getAll() {
		Session session = entityManager.unwrap(Session.class);
		List<User> users = session.createQuery("from User", User.class).getResultList();
		return users;
	}

	@Override
	public void add(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);		
	}

	@Override
	public void update(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);		
	}

	@Override
	public void delete(User user) {
		Session session = entityManager.unwrap(Session.class);
		User userToDelete = session.get(User.class, user.getId());
		session.delete(userToDelete);
	}

	@Override
	public User getById(int id) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, id);
		return user;
	}
	
	public User getByEmail(String email) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.createQuery("from User where email =:email", User.class)
	            .setParameter("email", email).uniqueResult();
		return user;
	}

	@Override
	public boolean isExist(User user) {
		Session session = entityManager.unwrap(Session.class);
		boolean isUserAlreadyExists = false;  
		Criteria crit = session.createCriteria(User.class);
		crit.setProjection(Projections.property("email"));
		List emails = crit.list();
		if(emails.contains(user.getEmail())){
	         isUserAlreadyExists = true; 
	     }
	     return isUserAlreadyExists;
	}

}
