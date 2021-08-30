package com.moviePage.movieP.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moviePage.movieP.Entities.Role;

@Repository
public interface IRoleDal extends JpaRepository<Role, Integer>{
 
	Role findByRole(String role);

}
