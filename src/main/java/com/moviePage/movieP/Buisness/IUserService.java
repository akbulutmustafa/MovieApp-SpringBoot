package com.moviePage.movieP.Buisness;

import java.util.List;

import com.moviePage.movieP.Entities.User;

public interface IUserService {
	
	List<User> getAll();
	void add(User user);
	void update(User user);
	void delete(User user);
	User getById(int id);
	boolean isExist(User user);
	User getByEmail(String email);
	
}
