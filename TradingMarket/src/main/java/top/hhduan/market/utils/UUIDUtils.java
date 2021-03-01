package top.hhduan.market.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author duanhaohao
 */
public class UUIDUtils {

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUid() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUid(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUid();
        }
        return ss;
    }

    /**
     * 获取count个随机数
     * @param count 随机数个数
     * @return
     */
    public static String game(int count){
        StringBuilder sb = new StringBuilder();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

}
