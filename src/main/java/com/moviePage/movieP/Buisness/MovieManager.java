package com.moviePage.movieP.Buisness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviePage.movieP.DataAccess.IMovieDal;
import com.moviePage.movieP.Entities.Movie;
import com.moviePage.movieP.Entities.User;

@Repository
public class MovieManager implements IMovieService{
	
	private IMovieDal movieDal;
	
	@Autowired
	public MovieManager(IMovieDal movieDal) {
		this.movieDal = movieDal;
	}

	@Override
	@Transactional
	public List<Movie> getAll() {
		return this.movieDal.getAll();
	}

	@Override
	@Transactional
	public void add(Movie movie, User user) {//, @AuthenticationPrincipal User user
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		user.addMovie(movie);
		Movie movieToAdd = this.movieDal.getById(movie.getId());
		if(movieToAdd==null) {
			this.movieDal.add(movie);
		}
	}

	@Override
	@Transactional
	public void update(Movie movie) {
		this.movieDal.update(movie);
	}

	@Override
	@Transactional
	public void delete(Movie movie) {
		this.movieDal.delete(movie);
	}

	@Override
	@Transactional
	public Movie getById(int id) {
		return this.movieDal.getById(id);
	}

	@Override
	public List<Movie> getMovies(int id) {
		return this.movieDal.getMovies(id);
	}
	
	

}
