package com.ef.parser.util;

import java.io.FileReader;
import java.util.Properties;

public class PropertyReader {

	private static Properties properties;
	private static String dbUrl;
	private static String dbUsername;
	private static String dbPassword;
	
	
	static {
		String propertyPath = System.getProperty("user.home") + System.getProperty("file.separator")
				+ IParserConstant.PROPERTIES_FILE_NAME;
		FileReader fileReader = null;
		try{
			fileReader = new FileReader(propertyPath);
			properties = new Properties();
			properties.load(fileReader);
			dbUrl = properties.getProperty("db.url");
			dbUsername = properties.getProperty("db.username");
			dbPassword = properties.getProperty("db.password");
			fileReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getdbUrl(){
		return dbUrl;
	}

	public static String getDbUsername() {
		return dbUsername;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	
	
}
