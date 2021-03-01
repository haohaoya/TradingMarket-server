package top.hhduan.market.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具
 * @author duanhaohao
 * @date 2020/8/7 14:50
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
