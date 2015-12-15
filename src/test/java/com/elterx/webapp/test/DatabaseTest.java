package com.elterx.webapp.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.elterx.webapp.Configuration;
import com.elterx.webapp.db.HibernateUtil;
import com.elterx.webapp.db.model.MedicalRecord;
import com.elterx.webapp.db.model.Pet;

public class DatabaseTest {
	private static DatabaseData data;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			Configuration.init();
			HibernateUtil.TRACE = true;
			HibernateUtil.init();
			Serializer serializer = new Persister();
			data = serializer.read(DatabaseData.class, DatabaseTest.class.getResourceAsStream("/testdata.xml"));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		HibernateUtil.close();
	}

	@Before
	public void setUp() throws Exception {
		populate();
	}

	@After
	public void tearDown() throws Exception {
		unpopulate();
	}

	@Test
	public void first3PetsHaveMedicalRecords() {
		Exception ex = HibernateUtil.exec((s,t)->{
			List<Long> petList1 = ((List<?>)s.createCriteria(Pet.class).addOrder(Order.asc("id")).list()).stream().map(o->((Pet)o).getId()).collect(Collectors.toList()).subList(0, data.getMedicalRecordList().size());
			List<Long> petList2 = ((List<?>)s.createCriteria(MedicalRecord.class).add(Restrictions.in("petId", petList1)).list()).stream().map(o->((MedicalRecord)o).getPetId()).collect(Collectors.toList());
			Collections.sort(petList1);
			Collections.sort(petList2);
			assertArrayEquals(petList1.toArray(new Long[]{}), petList2.toArray(new Long[]{}));
		});
		assertTrue(ex == null);
	}
	
	private void populate() throws Exception{
		Exception ex = HibernateUtil.exec((s,t)->{
			for(Pet pet : data.getPetList()){
				s.save(pet);
			}
		});
		if(ex!=null) throw new Exception(ex);
		ex = HibernateUtil.exec((s,t)->{
			List<Pet> petList = ((List<?>)s.createCriteria(Pet.class).addOrder(Order.asc("id")).list()).stream().map(o->(Pet)o).collect(Collectors.toList());
			int i = 0;
			for(MedicalRecord record :  data.getMedicalRecordList()){
				record.setPetId(petList.get(i++).getId());
				s.save(record);
			}
		});
		if(ex!=null) throw new Exception(ex);
	}
	private void unpopulate() throws Exception{
		Exception ex = HibernateUtil.exec((s,t)->{
			List<Pet> petList = ((List<?>)s.createCriteria(Pet.class).list()).stream().map(o->(Pet)o).collect(Collectors.toList());
			List<MedicalRecord> medicalRecordList = ((List<?>)s.createCriteria(MedicalRecord.class).list()).stream().map(o->(MedicalRecord)o).collect(Collectors.toList());
			for(Pet pet : petList) s.delete(pet);
			for(MedicalRecord record: medicalRecordList) s.delete(record);
		});
		if(ex!=null) throw new Exception(ex);
	}
}
