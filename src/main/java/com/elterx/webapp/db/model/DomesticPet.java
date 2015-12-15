package com.elterx.webapp.db.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class DomesticPet extends Pet{
	private String breed;
	@Enumerated(EnumType.STRING)
	private DomesticSpecies species;
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public DomesticSpecies getSpecies() {
		return species;
	}
	public void setSpecies(DomesticSpecies species) {
		this.species = species;
	}
}
