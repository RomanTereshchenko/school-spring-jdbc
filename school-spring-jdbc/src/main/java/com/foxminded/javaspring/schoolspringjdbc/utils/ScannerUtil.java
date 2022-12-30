package com.foxminded.javaspring.schoolspringjdbc.utils;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScannerUtil {

	private Scanner scan;

	@Autowired
	public ScannerUtil(Scanner scan) {
		this.scan = scan;
	}

	public int scanInt() {
		int scannedInt = scan.nextInt();
		scan.nextLine(); // nextInt() does not consume the last newline character,
//		and thus that newline is consumed in the next call to nextLine()
		return scannedInt;
	}

	public String scanString() { // Needed because Scanner is a final class, therefore 1. It can't be mocked. 2.
									// The instance of it can't be used by two different objects (scannerUtil and
									// studentService in StudentServiceTest).
		return scan.nextLine();
	}
}
