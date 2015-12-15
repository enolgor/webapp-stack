package com.elterx.webapp.db.model;

import javax.persistence.Entity;

@Entity
public class ExoticPet extends Pet{
	private String species;
	private Integer precautionLevel;
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public Integer getPrecautionLevel() {
		return precautionLevel;
	}
	public void setPrecautionLevel(Integer precautionLevel) {
		this.precautionLevel = precautionLevel;
	}
}
