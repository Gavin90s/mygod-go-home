package org.app.ticket.util;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.text.JTextComponent;

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
			}
		}
		return msg;
	}
}
