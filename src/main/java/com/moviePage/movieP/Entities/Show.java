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
@Table(name="tvshow")
public class Show {
	
	@Id
	@Column(name="show_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="score")
	private double score;
	
	@Column(name="genre")
	private String genre;
	
	@Lob
	@Column(name="summary")
	private String summary;
	
	@Column(name="imgsrc")
	private String imgSrc;
	
	@Column(name="firstair")
	private String airDate;
	
	@Column(name="backimg")
	private String imgBack;
	
	@Column(name="tmdbid")
	private String tmdbId;
	
	@Column(name="did")
	private boolean did = false;
	
	@ManyToMany(mappedBy = "tvshows")
	private List<User> users;

	public Show() {
		
	}
	
	public Show(int id, String title, double score, String genre, String summary, String imgSrc, String airDate,
			String imgBack, String tmdbId, List<User> users, boolean did) {
		this.id = id;
		this.title = title;
		this.score = score;
		this.genre = genre;
		this.summary = summary;
		this.imgSrc = imgSrc;
		this.airDate = airDate;
		this.imgBack = imgBack;
		this.tmdbId = tmdbId;
		this.did = did;
		this.users = users;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getAirDate() {
		return airDate;
	}

	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}

	public String getImgBack() {
		return imgBack;
	}

	public void setImgBack(String imgBack) {
		this.imgBack = imgBack;
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}

	public boolean isDid() {
		return did;
	}

	public void setDid(boolean did) {
		this.did = did;
	}
	
}
