package top.hhduan.market.utils;

import java.util.Date;
import java.util.Objects;

/**
 * @description: Token工具类
 * @author: duanhaohao
 * @create: 2020/8/10
 **/
public class TokenUtils {

    /**
     * 根据用户id及当前时间生成token
     *
     * @param userId
     * @return
     */
    public static String getToken(String userId) {
        String md5Str = userId + DateUtils.getDate(new Date());
        return Objects.requireNonNull(Md5Utils.stringToMd5(md5Str)).toUpperCase();
    }

}
