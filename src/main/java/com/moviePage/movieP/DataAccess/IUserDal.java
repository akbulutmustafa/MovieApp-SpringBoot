package com.moviePage.movieP.DataAccess;

import java.util.List;

import com.moviePage.movieP.Entities.User;

public interface IUserDal {
	
	List<User> getAll();
	void add(User user);
	void update(User user);
	void delete(User user);
	User getById(int id);
	boolean isExist(User user);
	User getByEmail(String email);

}
