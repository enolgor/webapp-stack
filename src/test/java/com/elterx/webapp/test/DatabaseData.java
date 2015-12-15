package com.elterx.webapp.test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.elterx.webapp.db.model.DomesticPet;
import com.elterx.webapp.db.model.DomesticSpecies;
import com.elterx.webapp.db.model.ExoticPet;
import com.elterx.webapp.db.model.MedicalRecord;
import com.elterx.webapp.db.model.Pet;
import com.elterx.webapp.db.model.Sex;

@Root(name = "database-data")
public class DatabaseData {
	@ElementList(name = "pets")
	private List<DataPet> petList;
	public List<Pet> getPetList(){ return petList.stream().map(dp -> dp.getAsPet()).collect(Collectors.toList()); }
	@ElementList(name = "medical-records")
	private List<DataMedicalRecord> medicalRecordList;
	public List<MedicalRecord> getMedicalRecordList(){ return medicalRecordList.stream().map(mr -> mr.getAsMedicalRecord()).collect(Collectors.toList()); }
	
	@Root(name = "pet")
	public static class DataPet{
		@Attribute private String type;
		@Attribute private String name;
		@Attribute private String sex;
		@Attribute private String registered;
		@Attribute private String age;
		@Attribute private String comments;
		@Attribute private String species;
		@Attribute(required = false) private String breed;
		@Attribute(name = "precaution-level", required = false) private String precaution_level;
		public Pet getAsPet(){
			switch(type){
			case "domestic": return getAsDomesticPet();
			case "exotic": return getAsExoticPet();
			}
			return null;
		}
		private DomesticPet getAsDomesticPet(){
			DomesticPet dom = new DomesticPet();
			dom.setName(name);
			dom.setSex(Sex.valueOf(sex));
			dom.setRegisteredTimestamp(ZonedDateTime.parse(registered));
			dom.setEstimatedAgeMonths(Integer.parseInt(age));
			dom.setComments(comments);
			dom.setSpecies(DomesticSpecies.valueOf(species));
			dom.setBreed(breed);
			return dom;
		}
		private ExoticPet getAsExoticPet(){
			ExoticPet exo = new ExoticPet();
			exo.setName(name);
			exo.setSex(Sex.valueOf(sex));
			exo.setRegisteredTimestamp(ZonedDateTime.parse(registered));
			exo.setEstimatedAgeMonths(Integer.parseInt(age));
			exo.setComments(comments);
			exo.setSpecies(species);
			exo.setPrecautionLevel(Integer.parseInt(precaution_level));
			return exo;
		}
	}
	@Root(name = "medical-record")
	public static class DataMedicalRecord{
		@Attribute private String doctor;
		@Attribute private String timestamp;
		@Attribute private String summary;
		public MedicalRecord getAsMedicalRecord(){
			MedicalRecord mr = new MedicalRecord();
			mr.setDoctor(doctor);
			mr.setSummary(summary);
			mr.setTimestamp(ZonedDateTime.parse(timestamp));
			return mr;
		}
	}
}
