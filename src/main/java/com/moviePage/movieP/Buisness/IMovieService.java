package com.moviePage.movieP.Buisness;

import java.util.List;

import com.moviePage.movieP.Entities.Movie;
import com.moviePage.movieP.Entities.User;

public interface IMovieService {
	List<Movie> getAll();
	void add(Movie movie, User user);
	void update(Movie movie);
	void delete(Movie movie);
	Movie getById(int id);
	List<Movie> getMovies(int id);
}
