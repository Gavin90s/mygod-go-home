package org.app.ticket.msg;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

/**
 * 资源管理器
 * 
 * @Title: ResManager.java
 * @Description: org.app.ticket.msg
 * @Package org.app.ticket.msg
 * @author hncdyj123@163.com
 * @date 2012-9-29
 * @version V1.0
 * 
 */
public class ResManager {
	private static final String BUNDLE_NAME = "resources.ticketrob"; //$NON-NLS-1$
	private static final String IMAGES_PATH = "/resources/images/";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private static ResourceBundle EXIT_RESOURCE_BUNDLE = null;

	private ResManager() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static ImageIcon createImageIcon(String filename) {
		return new ImageIcon(getFileURL(filename));
	}

	public static URL getFileURL(String filename) {
		String path = IMAGES_PATH + filename;
		return ClassLoader.class.getResource(path);
	}

	public static void initProperties(String filePath) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		EXIT_RESOURCE_BUNDLE = new PropertyResourceBundle(in);
	}

	public static String getByKey(String key) {
		try {
			return EXIT_RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "";
		}
	}

	public static void main(String[] arg0) throws Exception {
		initProperties("E:\\config.properties");
		String[] keys = ResManager.getByKey("traincode").split(",");
		for (int i = 0; i < keys.length; i++) {
			System.out.println("key" + i + " = " + keys[i]);
		}
		System.out.println(ResManager.getByKey("traincode"));
		System.out.println(System.getProperty("user.dir"));
	}
}
