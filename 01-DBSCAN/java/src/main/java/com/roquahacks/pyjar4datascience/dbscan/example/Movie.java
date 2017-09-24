package com.roquahacks.pyjar4datascience.dbscan.example;

import java.util.Date;
import java.util.List;

import com.roquahacks.pyjar4datascience.dbscan.Matchable;

public class Movie implements Matchable<Movie> {

	private String name;
	private String studio;
	private Date appearanceDate;
	private List<Actor> mainActors;
	private float imdbRating;
	
	public Movie(String name, String studio, Date appearanceDate, 
			List<Actor> mainActors, float imdbRating) {
		this.name = name;
		this.studio = studio;
		this.appearanceDate = appearanceDate;
		this.mainActors = mainActors;
		this.imdbRating = imdbRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public Date getAppearanceDate() {
		return appearanceDate;
	}

	public void setAppearanceDate(Date appearanceDate) {
		this.appearanceDate = appearanceDate;
	}

	public List<Actor> getMainActors() {
		return mainActors;
	}

	public void setMainActors(List<Actor> mainActors) {
		this.mainActors = mainActors;
	}

	public float getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(float imdbRating) {
		this.imdbRating = imdbRating;
	}

	@Override
	public double match(Movie other) {
		return 0;
	}

}
