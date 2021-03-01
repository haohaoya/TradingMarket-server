package top.hhduan.market.constants;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/15 13:48
 * @description: redis常量
 * @version: 1.0
 */
public class RedisConstants {

    /**
     * redis key 前缀
     */
    public final static String REDIS_PREFIX = "SECONDARY:";

    /**
     * token有效时间 7天
     */
    public final static int TOKEN_EXPIRED = 7 * 24 * 60 * 60;

    /**
     * 用户信息缓存有效时间 7天
     */
    public final static int USER_INFO_EXPIRE = 7 * 24 * 60 * 60;

    /**
     * 短信验证码有效时间 5分钟
     */
    public final static int VERIFY_CODE_EXPIRE = 5 * 60;

    /**
     * 用户信息token redis
     */
    public final static String TOKEN = REDIS_PREFIX + "TOKEN:%s";

    /**
     * 用户信息 redis
     */
    public final static String USER_INFO = REDIS_PREFIX + "USER_INFO:%s";

    /**
     * 注册短信验证码 redis
     */
    public final static String REGISTER_VERIFY_CODE = REDIS_PREFIX + "REGISTER_VERIFY_CODE:%s";

    /**
     * 找回密码验证码 redis
     */
    public final static String RETRIEVE_PWD_VERIFY_CODE = REDIS_PREFIX + "RETRIEVE_PWD_VERIFY_CODE:%s";

    /**
     * 验证旧手机号验证码 redis
     */
    public final static String OLD_MOBILE_VERIFY_CODE = REDIS_PREFIX + "OLD_MOBILE_VERIFY_CODE:%s";

    /**
     * 绑定新手机号验证码 redis
     */
    public final static String NEW_MOBILE_VERIFY_CODE = REDIS_PREFIX + "NEW_MOBILE_VERIFY_CODE:%s";

    /**
     * 下单后 redis key
     */
    public final static String ORDER_ID = REDIS_PREFIX + "ORDER_ID:%s";



}
