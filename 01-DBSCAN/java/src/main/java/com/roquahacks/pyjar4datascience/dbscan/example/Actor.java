package com.roquahacks.pyjar4datascience.dbscan.example;

import java.util.Date;

import com.roquahacks.pyjar4datascience.dbscan.Matchable;

public class Actor implements Matchable<Actor>{

	private String firstName;
	private String lastName;
	private Date birthDate;
	
	
	public Actor(String firstName, String lastName, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	@Override
	public double match(Actor other) {
		return 0;
	}
	
}
