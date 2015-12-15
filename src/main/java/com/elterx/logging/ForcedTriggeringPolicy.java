package com.elterx.logging;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;


@Plugin(name = "ForcedTriggeringPolicy", category = "Core", printObject = true)
public class ForcedTriggeringPolicy implements TriggeringPolicy{
	
	private static AtomicBoolean forceRoll = new AtomicBoolean(false);
	public static void roll(){
		forceRoll.compareAndSet(false, true);
	}
	
	
	@Override
	public void initialize(RollingFileManager rfm) {
		
	}

	@Override
	public boolean isTriggeringEvent(LogEvent arg0) {
		return forceRoll.compareAndSet(true, false);
	}

	@PluginFactory
	public static ForcedTriggeringPolicy createPolicy(){
		return new ForcedTriggeringPolicy();
	}	
}
