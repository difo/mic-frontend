package it.polimi.modaclouds.cloudapp.mic.servlet;

import it.polimi.modaclouds.monitoring.appleveldc.AppDataCollectorFactory;
import it.polimi.modaclouds.monitoring.appleveldc.ConfigurationException;
import it.polimi.modaclouds.monitoring.appleveldc.examples.FakeServletExample.FakeServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitDataCollectorServlet implements ServletContextListener {
	
	Logger logger = LoggerFactory.getLogger(InitDataCollectorServlet.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			AppDataCollectorFactory.initialize(FakeServlet.class.getPackage()
					.getName());
			AppDataCollectorFactory.getInstance().start();
		} catch (ConfigurationException e) {
			logger.error("Failed to start application level data collector", e);
		}
	}

}
