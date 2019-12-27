package com.github.datafinance.util;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

@WebListener
public class Initializer implements ServletContextListener {

	private static final Logger log = Logger.getLogger(Initializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			log.info("*********** Initializing... " + sce.getServletContext().getContextPath() + " ************");
			log.info(SystemProperties.get("base.dir"));
			initDatabase(false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void initDatabase(Boolean asynchronous) {
		if (asynchronous) {
			Thread t = new Thread(() -> {
				EMFUtil.initEMF(false);
			});
			t.start();
		} else {
			EMFUtil.initEMF(false);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("*********** Finalizing in path [" + sce.getServletContext().getContextPath() + "] ************");
		EMFUtil.closeEMF();
	}

	public static class SystemProperties {

		static Properties properties;
		static {
			try {
				properties = new Properties();
				properties.load(Initializer.class.getClassLoader().getResourceAsStream("system.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public static String get(String key) {
			return properties.getProperty(key);
		}
	}

}
