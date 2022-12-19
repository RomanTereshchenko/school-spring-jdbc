package com.foxminded.javaspring.schoolspringjdbc.controller;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class ScannerUtil {

	private Scanner scan = new Scanner(System.in);

	int scanInt() {
		int scannedInt = scan.nextInt();
		scan.nextLine(); // nextInt() does not consume the last newline character, 
//		and thus that newline is consumed in the next call to nextLine()
		return scannedInt;
	}
}
