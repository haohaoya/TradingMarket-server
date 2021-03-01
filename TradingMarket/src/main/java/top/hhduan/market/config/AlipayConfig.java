package top.hhduan.market.config;

/**
 * @author duanhaohao
 */
public class AlipayConfig {
	/** 商户appid */
	public static String APPID = "";
	/**
	 * 私钥 pkcs8格式的
	 */
	public static String RSA_PRIVATE_KEY = "";
	/**
	 * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	 */
	public static String NOTIFY_URL = "";
	/**
	 * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
 	 */
	public static String RETURN_URL = "";
	/**
	 * 请求网关地址
 	 */
	public static String URL = "https://openapi.alipay.com/gateway.do";
	//	public static String URL = "https://openapi.alipaydev.com/gateway.do"

	/** 编码 */
	public static String CHARSET = "UTF-8";
	/** 返回格式 */
	public static String FORMAT = "json";
	/** 支付宝公钥 */
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjTUIK45jDaO4xax8ZOPtfBiRz4Rwv78OFfkgwgMZq4FhPNEwBHmijIdjaKcxGl/cE2IIsm3huqdb7howj95P/nUorTew6VFxLCkxTW3f5PYuvzp44qeKvirBQkrccLIqpdnQ5M7kMdmI9dXc3HPG4VO+ZD7Zi73gXEzGlkbioz5fwzVpGfAFjvCzb49s1kxgEeIwAFLw6isiNJWAddzxagabbs6tVVM8XD/T9WmXyK00cASvqNd0TCeDO73ssW4hBch671MQkp5nbb8ZNuzZhU5/mkKuVjZk6jtqoABXhkfj6J5TNu6krNxDoPPK3nBkX+yOt1LMEFNQC8od0OTUrwIDAQAB";
//	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";
	/** 日志记录目录 */
	public static String LOG_PATH = "/log";
	/** RSA2 */
	public static String SIGNTYPE = "RSA2";
}
