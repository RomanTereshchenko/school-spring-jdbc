package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class ScannerUtil {

	private Scanner scan = new Scanner(System.in);

	public int scanInt() {
		int scannedInt = scan.nextInt();
		scan.nextLine(); // nextInt() does not consume the last newline character, 
//		and thus that newline is consumed in the next call to nextLine()
		return scannedInt;
	}
}
