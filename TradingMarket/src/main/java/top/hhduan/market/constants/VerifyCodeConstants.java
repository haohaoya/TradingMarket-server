package top.hhduan.market.constants;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/11/12 10:24
 * @description: 短信验证码业务类型常量
 * @version: 1.0
 */
public class VerifyCodeConstants {

    /**
     * 注册获取验证码
     */
    public final static int REGISTER = 1;

    /**
     * 找回密码获取验证码
     */
    public final static int RETRIEVE_PWD = 2;

    /**
     * 验证旧手机号获取验证码
     */
    public final static int OLD_MOBILE = 3;

    /**
     * 绑定新手机号获取验证码
     */
    public final static int NEW_MOBILE = 4;

}
