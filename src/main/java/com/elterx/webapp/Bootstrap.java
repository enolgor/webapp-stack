package com.elterx.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elterx.logging.ForcedTriggeringPolicy;
import com.elterx.webapp.db.HibernateUtil;

public class Bootstrap implements ServletContextListener{
	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try{
			HibernateUtil.close();
			logger.info("Database closed.");
		}catch(Exception e){
			logger.error("Database stop error: ",e);
		}
	}
	@Override
	public void contextInitialized(ServletContextEvent context_event) {
		try {
			Configuration.init();
			ForcedTriggeringPolicy.roll();
			//HibernateUtil.TRACE = true;
			HibernateUtil.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Initialized");
	}
}