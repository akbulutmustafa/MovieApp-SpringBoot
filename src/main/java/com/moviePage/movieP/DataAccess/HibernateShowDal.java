package com.moviePage.movieP.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviePage.movieP.Entities.Movie;
import com.moviePage.movieP.Entities.Show;

@Repository
public class HibernateShowDal implements IShowDal {
	
	private EntityManager entityManager;
	
	@Autowired
	public HibernateShowDal(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Show> getAll() {
		Session session = entityManager.unwrap(Session.class);
		List<Show> shows  = session.createQuery("from Show", Show.class).getResultList();
		return shows;
	}

	@Override
	public void add(Show show) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(show);
	}

	@Override
	public void update(Show show) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(show);
	}

	@Override
	public void delete(Show show) {
		Session session = entityManager.unwrap(Session.class);
		Show showToDelete = session.get(Show.class, show.getId());
		session.delete(showToDelete);
	}

	@Override
	public Show getById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Show show = session.get(Show.class, id);
		return show;
	}

	@Override
	public List<Show> getShows(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("select s from Show s join s.users u where u.id=:id").setParameter("id", id);
		List<Show> shows = (List<Show>) query.list();
		return shows;
	}

}
