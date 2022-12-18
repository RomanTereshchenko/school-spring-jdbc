package com.foxminded.javaspring.schoolspringjdbc.controller;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class Util {

	private Scanner scan = new Scanner(System.in);

	int scanInt() {
		int scannedInt = scan.nextInt();
		scan.nextLine();
		return scannedInt;
	}
}
