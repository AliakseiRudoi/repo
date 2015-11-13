package com.epam.rudoi.newsManagement.controller.util;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
		.getBundle("resources.config");

private ConfigurationManager() {

}

public static String getProperty(String key) {
	return RESOURCE_BUNDLE.getString(key);
}
    
}
