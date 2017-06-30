package com.pzj.trade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobStart {
	private final static Logger logger = LoggerFactory.getLogger(JobStart.class);

	public static void main(final String[] args) {
		logger.debug("定时系统启动");
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring/applicationContext*.xml");

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				context.stop();
				context.close();
				System.out.println("Task stop!!!!!!");
			}
		}, "Task-Hook"));

	}

}
