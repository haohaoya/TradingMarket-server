package top.hhduan.market.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 读取config.properties配置
 *
 * @author duanhaohao
 *
 */
class PropertiesUtil {

	private static Properties properties = new Properties();
	static {
		InputStream in = PropertiesUtil.class.getResourceAsStream("/application.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String get(String key) {
		return properties.getProperty(key).trim();
	}
}
