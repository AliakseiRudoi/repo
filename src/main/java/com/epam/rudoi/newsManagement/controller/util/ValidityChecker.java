package com.epam.rudoi.newsManagement.controller.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidityChecker {

	
	public static boolean checkValidity(String data, String validityConstans) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(validityConstans);
		Matcher matcher = pattern.matcher(data);
		if (matcher.matches()) {
			flag = true;
		}
		return flag;
	}
	
}
