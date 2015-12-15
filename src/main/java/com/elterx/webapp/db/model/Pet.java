package com.elterx.webapp.db.model;

import java.time.ZonedDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance
@DiscriminatorColumn(name="pet_type")
public abstract class Pet {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private ZonedDateTime registeredTimestamp;
	private String name;
	private Integer estimatedAgeMonths;
	private String comments;
	@Enumerated(EnumType.ORDINAL)
	private Sex sex;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ZonedDateTime getRegisteredTimestamp() {
		return registeredTimestamp;
	}
	public void setRegisteredTimestamp(ZonedDateTime registeredTimestamp) {
		this.registeredTimestamp = registeredTimestamp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getEstimatedAgeMonths() {
		return estimatedAgeMonths;
	}
	public void setEstimatedAgeMonths(Integer estimatedAgeMonths) {
		this.estimatedAgeMonths = estimatedAgeMonths;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
}
