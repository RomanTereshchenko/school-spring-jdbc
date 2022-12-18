package com.foxminded.javaspring.schoolspringjdbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;

@Component
@ConditionalOnProperty(prefix = "application", name = "env", havingValue = "prod")
public class ApplicationStartupRunner implements CommandLineRunner {
	
	private Controller controller;

	public ApplicationStartupRunner(Controller controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void run(String... args) throws Exception {
		controller.startUp();
		controller.menu();
	}
	
	
	

}
