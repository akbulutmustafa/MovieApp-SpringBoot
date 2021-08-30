package com.moviePage.movieP.Entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


@Entity
@Table(name="user")
public class User {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message="Enter first name")
	@Column(name="name")
	private String name;
	
	@NotNull(message="Enter last name")
	@Column(name="lastname")
	private String lastname;
	
	@NotNull(message="Enter an email")
	@Email(message="Email is invalid")
	@Column(name="email")
	private String email;
	
	@NotNull(message="Please enter a password")
	@Length(min=8, message="Pasword should be at least 8 characters")
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private String status;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="user_role", 
			joinColumns = @JoinColumn(name="user_id"), 
			inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	@ManyToMany
	@JoinTable(
			name="user_movie", 
			joinColumns =@JoinColumn(name="user_id"), 
			inverseJoinColumns=@JoinColumn(name="movie_id"))
	private List<Movie> movies;
	
	@ManyToMany
	@JoinTable(
			name="user_show",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="show_id"))
	private List<Show> tvshows;

	public User(int id, String name, String lastname, String email, String password, String status, Set<Role> roles, List<Movie> movies, List<Show> tvshows) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.status = status;
		this.roles = roles;
		this.movies = movies;
		this.tvshows = tvshows;
	}

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	public void addMovie(Movie movie) {
		this.movies.add(movie);
	}
	
	public void deleteMovie(Movie movie) {
		this.movies.remove(movie);
	}
	
	public List<Show> getShows() {
		return tvshows;
	}

	public void setShows(List<Show> tvshows) {
		this.tvshows = tvshows;
	}
	
	public void addShow(Show show) {
		this.tvshows.add(show);
	}
	
	public void deleteShow(Show show) {
		this.tvshows.remove(show);
	}
	
}
