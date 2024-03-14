package edu.kh.emp.common;

import java.io.FileOutputStream;
import java.util.Properties;

public class CreateXMLFile {
	
	public static void main(String[] args) {
		try {
			
			Properties prop = new Properties();
			
			FileOutputStream fos = new FileOutputStream("driver.xml");
			
			prop.storeToXML(fos, "driver.xml");

			fos = new FileOutputStream("query.xml");
			
			prop.storeToXML(fos, "query.xml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
