package com.foxminded.javaspring.schoolspringjdbc;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;

public class EntryPoint {
	
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		
		controller.startUp();
		controller.menu();

	}

}
