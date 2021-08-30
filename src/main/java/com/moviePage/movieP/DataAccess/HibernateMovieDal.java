package com.moviePage.movieP.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviePage.movieP.Entities.Movie;

@Repository
public class HibernateMovieDal implements IMovieDal{
	
	private EntityManager entityManager;
	
	@Autowired
	public HibernateMovieDal(EntityManager entityManager) {
		this.entityManager=entityManager;
	}

	@Override
	@Transactional
	public List<Movie> getAll() {
		Session session = entityManager.unwrap(Session.class);
		List<Movie> movies  = session.createQuery("from Movie", Movie.class).getResultList();
		return movies;
	}

	@Override
	public void add(Movie movie) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(movie);
	}

	@Override
	public void update(Movie movie) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(movie);
	}

	@Override
	public void delete(Movie movie) {
		Session session = entityManager.unwrap(Session.class);
		Movie movieToDelete = session.get(Movie.class, movie.getId());
		session.delete(movieToDelete);
	}

	@Override
	public Movie getById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Movie movie = session.get(Movie.class, id);
		return movie;
	}
	
	@Override
	@Transactional
	public List<Movie> getMovies(int id) {
		Session session = entityManager.unwrap(Session.class);		
		Query query = session.createQuery("select m from Movie m join m.users u where u.id=:id").setParameter("id", id);//.getResultList();
		List<Movie> movies = (List<Movie>) query.list();
		/*String hql = "from Movie m join m.users u where u.id = "+ String.valueOf(id);
		movies = session.createQuery(hql).list();*/		
		return movies;
	}

}
