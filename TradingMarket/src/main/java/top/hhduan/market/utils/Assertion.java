package top.hhduan.market.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * 断言工具类
 *
 * @author duanhaohao
 */
public class Assertion extends Assert {

    private final static Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(14[5-8])|(15[0-3,5-9])|(17[0-8])|(18[0-9])|166|198|199|)\\d{8}$");

    private final static Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    public static void isNegative(double value, String message) {
        if (!Detect.isNegative(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    private static void isPositive(double value, String message) {
        if (!Detect.isPositive(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isPositive(Double value, String message) {
        if (!Detect.isPositive(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isPositive(Long value, String message) {
        if (!Detect.isPositive(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isPositive(Integer value, String message) {
        if (!Detect.isPositive(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * notEmpty
     */
    public static void notEmpty(String[] string, String message) {
        if (!Detect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(long[] values, String message) {
        if (!Detect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(int[] values, String message) {
        if (!Detect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(short[] values, String message) {
        if (!Detect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(List<?> list, String message) {
        if (!Detect.notEmpty(list)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String string, String message) {
        if (!Detect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Date date, String message) {
        if (null == date) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(String[] string, String message) {
        if (Detect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(Integer[] values, String message) {
        if (Detect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(long[] values, String message) {
        if (Detect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(List<?> list, String message) {
        if (Detect.notEmpty(list)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(String string, String message) {
        if (Detect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * onlyOne
     */
    public static void onlyOne(List<?> list, String message) {
        if (!Detect.onlyOne(list)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void equals(Object obj1, Object obj2, String message) {
        if (null == obj1 && null == obj2) {
            return;
        }
        if (null != obj1 && obj1.equals(obj2)) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

    /**
     * 如果两者是同一个则报错
     *
     * @param obj1
     * @param obj2
     * @param message
     */
    public static void notEquals(Object obj1, Object obj2, String message) {
        if (null == obj1 && null == obj2) {
            throw new IllegalArgumentException(message);
        }
        if (null != obj1 && obj1.equals(obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNullAndNotEquals(Object obj1, Object obj2, String message) {
        if (null == obj1 || null == obj2) {
            throw new IllegalArgumentException(message);
        }
        if (obj1.equals(obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value是否是纯数字
     *
     * @param value
     * @param message
     */
    public static void isDigits(String value, String message) {
        if (!NumberUtils.isDigits(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否大于length
     *
     * @param value
     * @param length
     * @param message
     */
    public static void greaterThanLength(String value, int length, String message) {
        if (value.length() <= length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否大于等于length
     *
     * @param value
     * @param length
     * @param message
     */
    public static void greaterThanOrEqualLength(String value, int length, String message) {
        if (value.length() < length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否小于length
     *
     * @param value
     * @param length
     * @param message
     */
    public static void lessThanLength(String value, int length, String message) {
        if (value.length() >= length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否小于等于length
     *
     * @param value
     * @param length
     * @param message
     */
    public static void lessThanOrEqualLength(String value, int length, String message) {
        if (value.length() > length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value长度是否在开区间内
     *
     * @param value
     * @param min
     * @param max
     * @param message
     */
    public static void betweenLength(String value, int min, int max, String message) {
        if (min >= max) {
            throw new IllegalArgumentException("最小值: " + min + "不能大于等于最大值: " + max);
        }
        if (value.length() <= min || value.length() >= max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value长度是否在闭区间内
     *
     * @param value
     * @param min
     * @param max
     * @param message
     */
    public static void betweenOrEqualLength(String value, int min, int max, String message) {
        if (min > max) {
            throw new IllegalArgumentException("最小值: " + min + "不能大于最大值: " + max);
        }
        if (value.length() < min || value.length() > max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的字节长度是否小于等于length
     *
     * @param value
     * @param length
     * @param message
     */
    public static void lessThanOrEqualLengthInByte(String value, int length, String message) {
        if (value.getBytes().length > length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的字节长度是否等于length
     *
     * @param value
     * @param length
     * @param message
     */
    public static void lessEqualLength(String value, int length, String message) {
        if (Detect.notEmpty(value) && value.length() != length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断str不能包含数组keys中的字符串，否则抛错
     *
     * @param str
     * @param keys
     * @param message
     */
    public static void notContains(String str, String[] keys, String message) {
        if (null == str) {
            throw new IllegalArgumentException(message);
        }
        for (String key : keys) {
            if (str.contains(key)) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * 判断str取值是否在字符串数组列表中 如果str不匹配字符串数组中任意一个字符串则抛错
     *
     * @param str
     * @param enums
     * @param message
     */
    public static void in(String str, String[] enums, String message) {
        if (!Detect.notEmpty(enums)) {
            throw new IllegalArgumentException(message + "，枚举集合为空");
        }
        boolean match = false;
        for (String anEnum : enums) {
            if (null != anEnum && anEnum.equals(str)) {
                match = true;
                break;
            }
        }
        if (!match) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 如果str是以key结束的则ok，否则报错
     *
     * @param str
     * @param key
     * @param message
     */
    public static void endsWith(String str, String key, String message) {
        if (null == str || !str.endsWith(key)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 匹配正则表达式
     *
     * @param value
     * @param regexp
     * @param message
     */
    public static void matchRegexp(String value, String regexp, String message) {
        if (!value.matches(regexp)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 取得随机大写字母
     *
     * @return
     */
    public static char getUpperLetter() {
        int max = 90;
        int min = 65;
        Random random = new Random();
        int s;
        do {
            s = random.nextInt(max) % (max - min + 1) + min;
        } while (min > s);
        return (char) s;
    }

    /**
     * 自动生成时间序列
     *
     * @return
     */
    public static synchronized String getGeneraterNextNumber() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date) + String.format("%04d", Assertion.getCount());
    }

    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 生成序列
     *
     * @return
     */
    private static int getCount() {
        int c = counter.incrementAndGet();
        final int max = 9999;
        if (c == max) {
            counter.set(0);
        }
        return c;
    }

    /**
     * 正则匹配电话号码
     *
     * @param mobiles
     * @param message
     */
    public static void isMobile(String mobiles, String message) {

        if (!MOBILE_PATTERN.matcher(mobiles).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 匹配邮箱
     */
    public static void isEmail(String email, String message) {

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 身份证号码匹配
     */
    public static void isIdCard(String idCardNo, String message) {
        String str = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
        Pattern p = Pattern.compile(str);
        if (!p.matcher(idCardNo).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

}
