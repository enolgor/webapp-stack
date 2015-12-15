package com.elterx.webapp.db;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elterx.webapp.Configuration;
import com.elterx.webapp.Configuration.DatabaseConfiguration;

public class HibernateUtil {
	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
	
	public static boolean TRACE = false;
	private static SessionFactory sessionFactory;
	public static void init(){
		DatabaseConfiguration dbconf = Configuration.getInstance().getDatabaseConfiguration();
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .applySetting("hibernate.connection.url", dbconf.getUrl())
		        .applySetting("hibernate.connection.username", dbconf.getUsername())
		        .applySetting("hibernate.conneciton.password", dbconf.getPassword())
		        .build();
		sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
	}
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public static Exception exec(HibernateExec e){
		Session s = sessionFactory.openSession();
		Transaction t = null;
		try{
			t = s.beginTransaction();
			e.exec(s,t);
			s.flush();
			t.commit();
			s.close();
			return null;
		}catch(Exception ex){
			if(t!=null) t.rollback();
			if(TRACE) logger.error("Error performing database operation -", ex);
			else logger.error("Error performing database operation - "+ex.getMessage());
			return ex;
		}finally{
			if(s.isConnected()) s.close();
		}
	}
	public static void close(){
		sessionFactory.close();
	}
	
	/*@SuppressWarnings("unchecked")
	public static <T> List<T> all(Class<T> type){
		final Wrapper<List<T>> w = new Wrapper<>();
		safeExec((s,t)->{
			w.set(((List<?>)s.createQuery("from "+type.getName()).list()).stream().map(o->(T)o).collect(Collectors.toList()));
		});
		return w.get();
	}
	
	public static <T> boolean save(T item){
		return HibernateUtil.safeExec((s,t)->{
			s.save(item);
		});
	}
	*/
	public static interface HibernateExec{
		public void exec(Session s, Transaction t) throws Exception;
	}
	
	public static class Wrapper<T>{
		private T t = null;
		public T get(){return t;}
		public void set(T t){this.t = t;}
	}
}