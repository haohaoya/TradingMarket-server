package top.hhduan.market.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duanhaohao
 */
@Slf4j
public class JacksonUtils {
    /**
     * Object转Json
     */
    public static String objectToJson(Object value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error("Object转换Json异常:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Object转换Json异常:" + e.getMessage());
        }
    }

    /**
     * Json转Object（List）
     *
     * @param json 需要转换的JSON字符串
     * @param bean JavaBean,
     * @return 拿到结果需要强转一次，因为你拿到的是Object, 例如这样调用和强转： List&lt;School&gt; lst
     * =(List&lt;School&gt;)StringUtil.JsonToObjectList(value, School.class);
     */
    public static Object jsonToList(String json, Class<?> bean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, bean);
        return mapper.readValue(json, javaType);
    }

    /**
     * Json转Object（JavaBean）
     *
     * @param json 需要转换的JSON字符串
     * @param bean JavaBean,
     * @return 拿到结果需要强转一次，因为你拿到的是Object, 例如这样调用和强转： School lst
     * =(School)StringUtil.JsonToObjectList(value, School.class);
     */
    public static Object jsonToBean(String json, Class<?> bean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = mapper.getTypeFactory().uncheckedSimpleType(bean);
        return mapper.readValue(json, javaType);
    }

    /**
     * 将json格式的字符串解析成Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Map<String, Object>> jsonToMap(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            log.error("将json格式的字符串解析成Map对象异常:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("将json格式的字符串解析成Map对象异常:" + e.getMessage());
        }
    }

    /**
     * 将json转换成List<Map<String,Object>>
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonToListString(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, List.class);
        } catch (Exception e) {
            log.error("将json转换成List异常:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("将json转换成List异常:" + e.getMessage());
        }
    }

    /**
     * 将json转换成Map<String, Object>
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMapObject(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //空值不序列化
            mapper.setSerializationInclusion(Include.NON_NULL);
            //反序列化时，属性不存在的兼容处理
            mapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            log.error("将json转换成Map异常:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("将json转换成Map异常:" + e.getMessage());
        }
    }

    /**
     * 正则表达式验证
     *
     * @param value  需要验证的值
     * @param regexp 进行验证的正则表达式
     */
    public static boolean regExpTest(String value, String regexp) {
        //验证标识符必须由字母、数字、下划线组成
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * ISO-8859-1转UTF-8 主要用于POST数据处理
     *
     * @param str 需要转码的值
     */
    public static String encodeStr(String str) {
        final String iso = "ISO-8859-1";
        final String uft8 = "UTF-8";
        try {
            return new String(str.getBytes(iso), uft8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException 没有这种产生消息摘要的算法
     */
    public static String encoderByMd5(String str) throws NoSuchAlgorithmException {

        byte[] bs = MessageDigest.getInstance("MD5").digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    /**
     * 对文字进行编码处理
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encoderUrl(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("对文字进行编码处理异常:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("对文字进行编码处理异常:" + e.getMessage());
        }
    }

    /**
     * 对编码后的文字进行解码
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String decoderUrl(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("对编码后的文字进行解码异常:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("对编码后的文字进行解码异常:" + e.getMessage());
        }
    }

    /**
     * 根据key获取map中对应的value, 不存在则返回空
     *
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String mapValueToString(Map map, String key) {
        if (map != null && map.get(key) != null) {
            String str = map.get(key).toString();
            log.info("key = [" + key + "]对应的值为：" + str);
            return str;
        }
        log.info("key = [" + key + "]对应的值为空");
        return "";
    }

    /**
     * 将json强转未list<C>
     *
     * @param json
     * @param bean
     * @param <C>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <C> List<C> jsonToListC(String json, Class<C> bean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, bean);
        return (List<C>) mapper.readValue(json, javaType);
    }
}
