package com.moviePage.movieP.Buisness;

import java.util.List;

import com.moviePage.movieP.Entities.Show;
import com.moviePage.movieP.Entities.User;


public interface IShowService {
	
	List<Show> getAll();
	void add(Show show, User user);
	void update(Show show);
	void delete(Show show);
	Show getById(int id);
	List<Show> getShows(int id);

}
