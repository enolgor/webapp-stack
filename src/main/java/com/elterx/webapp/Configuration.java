package com.elterx.webapp;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Root(name = "webapp-configuration")
public class Configuration {
	static{
		try{
			System.getProperties().load(Configuration.class.getResourceAsStream("/system.properties"));
			LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			ctx.reconfigure();
		}catch(Exception e){
			System.err.println("Unable to load system.properties file");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	public static void init() throws Exception{
		String configuration_filepath = System.getProperty("application.data.path")+"/"+System.getProperty("application.name")+".cfg.xml";
		Serializer serializer = new Persister();
		File file = new File(configuration_filepath);
		if(file.exists()){
			instance = serializer.read(Configuration.class, file);
		}else{
			instance = serializer.read(Configuration.class, Configuration.class.getResourceAsStream("/"+System.getProperty("application.name")+".default.cfg.xml"));
			logger.warn("Configuration file "+configuration_filepath+" not found. Using default configuration.");
			file.getParentFile().mkdirs();
			file.createNewFile();
			serializer.write(instance, file);
			logger.info("Configuration file "+configuration_filepath+" created.");
		}
		instance.datapath = System.getProperty("application.data.path");
		instance.applicationName = System.getProperty("application.name");
	}
	
	public static Configuration getInstance(){
		if(instance == null)
			try{
				init();
			}catch(Exception e){
				logger.error("Cannot init configuration");
				e.printStackTrace();
				System.exit(-1);
			}
		return instance;
	}
	
	private static Configuration instance;
	
	private String datapath;
	public String getDataPath(){ return datapath; }
	
	private String applicationName;
	public String getApplicationName(){ return applicationName; }
	
	@Element( name = "database")
	private DatabaseConfiguration databaseConfiguration;
	public DatabaseConfiguration getDatabaseConfiguration(){ return databaseConfiguration; }
	
	@Root(name = "database")
	public static class DatabaseConfiguration{
		
		@Element
		private String url;
		public String getUrl(){ return url; }
		
		@Element
		private String username;
		public String getUsername(){ return username; }
		
		@Element
		private String password;
		public String getPassword(){ return password; }
	}
}
