package org.app.ticket.util;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.app.ticket.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title: ToolUtil.java
 * @Description: org.app.ticket.util
 * @Package org.app.ticket.util
 * @author hncdyj123@163.com
 * @date 2012-10-26
 * @version V1.0
 * 
 */
public class ToolUtil {
	private static final Logger logger = LoggerFactory.getLogger(ToolUtil.class);

	/**
	 * 获取ImageIcon
	 * 
	 * @param path
	 * @return
	 */
	public static ImageIcon getImageIcon(String path) {
		ImageIcon icon = new ImageIcon(path);
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
		return icon;
	}

	/**
	 * 验证控件是否为空
	 * 
	 * @return
	 */
	public static List<String> validateWidget(Object... o) {
		List<String> msg = new ArrayList<String>();
		if (o.length > 0) {
			for (Object s : o) {
				JTextComponent f = (JTextComponent) s;
				if (StringUtil.isEmptyString(f.getText().trim())) {
					msg.add(f.getToolTipText());
				}
				if (f.getClass() == JFormattedTextField.class) {
					if (StringUtil.isEqualString(Constants.SYS_STRING_DATEFORMAT, f.getText().trim())) {
						msg.add(f.getToolTipText());
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param fileName
	 * @param o
	 * @throws Exception
	 */
	public static void getUserInfo(String path, String fileName, Object... o) throws Exception {
		Map<String, String> userMap = new HashMap<String, String>();
		if (o.length > 0) {
			for (Object s : o) {
				JTextComponent f = (JTextComponent) s;
				if (!StringUtil.isEmptyString(f.getText().trim())) {
					if (f.getClass() == JTextField.class) {
						JTextField jtf = (JTextField) f;
						userMap.put(jtf.getName(), jtf.getText().trim());
					}
					if (f.getClass() == JPasswordField.class) {
						JPasswordField jpf = (JPasswordField) f;
						userMap.put(jpf.getName(), jpf.getText().trim());
					}
				}
			}
		}

		writeFile(userMap, path, fileName);
	}

	/**
	 * 将用户上一次使用信息写入文件
	 * 
	 * @param info
	 * @param fileName
	 * @throws Exception
	 */
	private static void writeFile(Map<String, String> userMap, String path, String fileName) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + fileName));
		out.writeObject(userMap);
		out.close();
	}

	/**
	 * 对控件进行赋值
	 * 
	 * @param path
	 * @param fileName
	 * @param o
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static void setUserInfo(String path, String fileName, Object... o) throws FileNotFoundException, Exception {
		if (!new File(path + fileName).exists()) {
			return;
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + fileName));
		Map<String, String> userMap = (Map<String, String>) in.readObject();
		in.close();
		if (o.length > 0) {
			for (Object s : o) {
				JTextComponent f = (JTextComponent) s;
				if (f.getClass() == JTextField.class) {
					JTextField jtf = (JTextField) f;
					jtf.setText(userMap.get(jtf.getName()) == null ? "" : userMap.get(jtf.getName()));
				}
				if (f.getClass() == JPasswordField.class) {
					JPasswordField jpf = (JPasswordField) f;
					jpf.setText(userMap.get(jpf.getName()) == null ? "" : userMap.get(jpf.getName()));
				}
			}
		}
	}
}
