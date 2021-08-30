package com.moviePage.movieP.DataAccess;

import java.util.List;

import com.moviePage.movieP.Entities.Movie;

public interface IMovieDal {
	List<Movie> getAll();
	void add(Movie movie);
	void update(Movie movie);
	void delete(Movie movie);
	Movie getById(int id);
	List<Movie> getMovies(int id);
}
