package com.moviePage.movieP.Buisness;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviePage.movieP.DataAccess.IShowDal;
import com.moviePage.movieP.Entities.Show;
import com.moviePage.movieP.Entities.User;

@Repository
public class ShowManager implements IShowService{
	
	private IShowDal iShowDal;
	
	@Autowired
	public ShowManager(IShowDal iShowDal) {
		this.iShowDal = iShowDal;
	}

	@Override
	@Transactional
	public List<Show> getAll() {
		return this.iShowDal.getAll();
	}

	@Override
	@Transactional
	public void add(Show show, User user) {
		user.addShow(show);
		Show showToAdd = this.iShowDal.getById(show.getId());
		if(showToAdd == null) {
			this.iShowDal.add(show);
		}
	}

	@Override
	@Transactional
	public void update(Show show) {
		this.iShowDal.update(show);
	}

	@Override
	@Transactional
	public void delete(Show show) {
		this.iShowDal.delete(show);
	}

	@Override
	@Transactional
	public Show getById(int id) {
		return this.iShowDal.getById(id);
	}

	@Override
	public List<Show> getShows(int id) {
		return this.iShowDal.getShows(id);
	}

}
