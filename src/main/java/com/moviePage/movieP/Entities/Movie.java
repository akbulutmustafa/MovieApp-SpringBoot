package com.moviePage.movieP.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="movie")
public class Movie {
	
	@Id
	@Column(name="movie_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="genre")
	private String genre;
	
	@Lob
	@Column(name="summary")
	private String summary;
	
	@Column(name="imdbid")
	private String imdbid;
	
	@Column(name="time")
	private String duration;
	
	@Column(name="score")
	private double score;

	@Column(name="relasedate")
	private String relaseDate;
	
	@Column(name="imgsource")
	private String imgSource;
	
	@Column(name="backImg")
	private String backImg;
	
	@Column(name="did")
	private boolean did = false;

	@ManyToMany(mappedBy = "movies")
	private List<User> users;
	
	/*@ManyToMany(mappedBy = "user")
	Set<User> users;
	public Movie(int id, String title) {
		this.id = id;
		this.title = title;
	}*/

	public Movie(int id, String title, String genre, String imdbid, String duration, double score, String relaseDate, String summary, List<User> users, boolean did) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.imdbid = imdbid;
		this.duration = duration;
		this.score = score;
		this.relaseDate = relaseDate;
		this.users = users;
		this.summary = summary;
		this.did = did;
	}

	public Movie() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getImdbid() {
		return imdbid;
	}

	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRelaseDate() {
		return relaseDate;
	}

	public void setRelaseDate(String relaseDate) {
		this.relaseDate = relaseDate;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getImgSource() {
		return imgSource;
	}

	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}

	public String getBackImg() {
		return backImg;
	}

	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public boolean isDid() {
		return did;
	}

	public void setDid(boolean did) {
		this.did = did;
	}
	
}
