package app.utils;

import java.net.URL;

import javax.swing.ImageIcon;

public class Utility {

	public static ImageIcon getIcon(String name) {
		try {
			URL url = Utility.class.getResource("/res/" + name);
			return new ImageIcon(url);
		} catch (Exception e) {
			return new ImageIcon("res/" + name);
		}		
	}
	
	public static String getAppPath() {
		try {
			URL url = Utility.class.getResource("/");
			return url.toURI().getPath();
		} catch (Exception e) {
			return null;
		}		
	}
	
	public static String checkDriverServer(String driver) {
		String driverServer = null;
		int index = driver.lastIndexOf(".");
		String ext = driver.substring(index + 1);
		if (!ext.equals("exe")) {
			driverServer = driver + ".exe";
		} else {
			driverServer = driver;
		}
		return driverServer;
	}
	
}
