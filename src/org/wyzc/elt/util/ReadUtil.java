package org.wyzc.elt.util;

import java.util.Properties;

public class ReadUtil {
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(ReadUtil.class.getResourceAsStream("/data.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getMessage(String propname) {
		//System.out.println(prop.getProperty(propname));
		return prop.getProperty(propname);
	}
}
