package com.github.datafinance.util;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.github.datafinance.util.Initializer.SystemProperties;

public class EMFUtil {

	private static final Logger log = Logger.getLogger(EMFUtil.class);
	private static final String PERSISTENCE_UNIT_NAME = "pu-data-finance";

	private static EntityManagerFactory emf;

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		EntityManager em = emf.createEntityManager();
		log.debug(String.format("The EntityManager [%s] was instantiated.", em.toString()));
		return em;
	}

	public void closeEntityManager(@Disposes EntityManager em) {
		log.debug(String.format("Close the EntityManager [%s].", em.toString()));
		em.close();
	}

	private static Map<?, ?> getProperties(Boolean generateDataBase) {

		Map<String, Object> properties = new HashMap<>();

		if (generateDataBase) {
			properties.put("javax.persistence.schema-generation.database.action", "create");
			properties.put("javax.persistence.schema-generation.create-source", "script-then-metadata");
			String baseDir = SystemProperties.get("base.dir");
			properties.put("javax.persistence.schema-generation.create-script-source", baseDir + "/src/main/resources/META-INF/sql-generation/create.sql ");
			properties.put("javax.persistence.sql-load-script-source", baseDir + "/src/main/resources/META-INF/sql-generation/data.sql ");
			properties.put("javax.persistence.jdbc.url", SystemProperties.get("jdbc.url"));
		}
		else {
			properties.put("javax.persistence.jdbc.url", SystemProperties.get("jdbc.url")+";readonly=true");
		}
		properties.put("hibernate.default_schema", "DATA_FINANCE");

		// JDBC CONFIGURATION
		properties.put("javax.persistence.jdbc.driver", SystemProperties.get("jdbc.driver"));
		properties.put("javax.persistence.jdbc.user", SystemProperties.get("jdbc.user"));
		properties.put("javax.persistence.jdbc.password", SystemProperties.get("jdbc.password"));

		return properties;
	}

	public static EntityManagerFactory getEMF() {
		return emf;
	}

	public static EntityManager getEM() {
		EntityManager em = emf.createEntityManager();
		log.debug(String.format("The EntityManager [%s] was instantiated.", em.toString()));
		return em;
	}

	public static void closeEM(EntityManager em) {
		try {
			if (em != null && em.isOpen()) {
				log.debug(String.format("Close the EntityManager [%s].", em.toString()));
				em.close();
			}
		} catch (Exception e) {
		}
	}

	public static void initEMF() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, getProperties(false));
		}
	}

	public static void initEMF(boolean generateDataBase) {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, getProperties(generateDataBase));
		}
	}

	public static void closeEMF() {
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}

}
