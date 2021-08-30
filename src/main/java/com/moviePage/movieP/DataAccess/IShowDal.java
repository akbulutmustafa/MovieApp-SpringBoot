package com.moviePage.movieP.DataAccess;

import java.util.List;

import com.moviePage.movieP.Entities.Show;

public interface IShowDal {
	
	List<Show> getAll();
	void add(Show show);
	void update(Show show);
	void delete(Show show);
	Show getById(int id);
	List<Show> getShows(int id);

}
