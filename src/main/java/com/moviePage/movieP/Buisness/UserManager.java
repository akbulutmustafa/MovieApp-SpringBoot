package com.moviePage.movieP.Buisness;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviePage.movieP.DataAccess.IRoleDal;
import com.moviePage.movieP.DataAccess.IUserDal;
import com.moviePage.movieP.Entities.Role;
import com.moviePage.movieP.Entities.User;

@Service
public class UserManager implements IUserService{
	
	private IUserDal userDal;
	
	@Autowired
	private IRoleDal roleDal;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserManager(IUserDal userDal) {
		this.userDal = userDal;
	}

	@Override
	@Transactional
	public List<User> getAll() {
		return this.userDal.getAll();
	}

	@Override
	@Transactional
	public void add(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setStatus("VERIFIED");
		//Role userRole = roleDal.getById(3);
		Role userRole = roleDal.findByRole("SITE_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		this.userDal.add(user);
	}
	
	/*@Transactional
	public void passwordRec(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	}*/

	@Override
	@Transactional
	public void update(User user) {
		this.userDal.update(user);
	}

	@Override
	@Transactional
	public void delete(User user) {
		this.userDal.delete(user);
	}

	@Override
	@Transactional
	public User getById(int id) {
		return this.userDal.getById(id);
	}
	
	@Override
	@Transactional
	public User getByEmail(String email) {
		return this.userDal.getByEmail(email);
	}

	@Override
	public boolean isExist(User user) {
		return userDal.isExist(user);
	}

}
