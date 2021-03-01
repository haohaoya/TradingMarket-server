package top.hhduan.market.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author duanhaohao
 */
public class Detect {

    private static final String DELIMITER = ",";

    public static <T> byte[] convertToByteArray(T t) {
        byte[] bytes = null;
        if (t != null) {
            ByteArrayOutputStream byteArrayOutputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(t);
                bytes = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bytes;
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(byte[] bytes) {
        T t = null;
        if (bytes != null) {
            ObjectInputStream objectInputStream = null;
            try (ByteArrayInputStream byteArrayInputputStream = new ByteArrayInputStream(bytes)) {
                objectInputStream = new ObjectInputStream(byteArrayInputputStream);

                t = (T) objectInputStream.readObject();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return t;
    }

    /*public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }*/

    public static boolean bizIdEquals(long bizId1, long bizId2) {
        return bizId2 > 0 && bizId1 == bizId2;
    }

    public static boolean bizIdEquals(double bizId1, double bizId2) {
        return bizId1 > 0 && bizId2 > 0 && Math.abs(bizId1 - bizId2) == 0.0d;
    }

    public static boolean notEmpty(String string) {
        return null != string && !"".equals(string) && !"\"null\"".equals(string) && !"null".equals(string);
    }

    public static boolean notEmpty(byte[] bytes) {
        return (null != bytes && 0 < bytes.length);
    }

    public static boolean notEmpty(List<?> list) {
        return null != list && !list.isEmpty();
    }

    public static boolean notEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    public static boolean notEmpty(Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    public static boolean notEmpty(short[] array) {
        return null != array && array.length > 0;
    }

    public static boolean notEmpty(int[] array) {
        return null != array && array.length > 0;
    }

    public static boolean notEmpty(long[] array) {
        return null != array && array.length > 0;
    }

    public static boolean notEmpty(String[] array) {
        return null != array && array.length > 0;
    }

    public static <T> boolean notEmpty(T[] array) {
        return null != array && array.length > 0;
    }

    public static boolean isNegative(Double value) {
        return value < 0;
    }

    public static boolean isPositive(Double value) {
        return value != null && value > 0;
    }

    public static boolean isPositive(Long value) {
        return value != null && value > 0;
    }

    public static boolean isPositive(Integer value) {
        return value != null && value > 0;
    }

    public static boolean isPositive(Short value) {
        return value != null && value > 0;
    }

    public static boolean isPositive(Float value) {
        return value != null && value > 0;
    }

    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    private static boolean contains(long value, long[] values) {
        if (notEmpty(values)) {
            int length = values.length;
            for (long l : values) {
                if (l == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /*public static boolean containsAll(long[] values, long[] subValues) {
        if (!notEmpty(values) && !notEmpty(subValues)) {
            return true;
        }
        if (notEmpty(subValues)) {
            for (long subValue : subValues) {
                if (!contains(subValue, values)) {
                    return false;
                }
            }
            return true;
        } else if (notEmpty(values)) {
            return true;
        }
        return false;
    }*/

    public static <E> boolean contains(E one, List<E> list) {
        if (notEmpty(list) && null != one) {
            for (E item : list) {
                if (one.equals(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * array
     */
    public static double[] doubleArray(String value, String delimiter) {
        String[] values = StringUtils.split(value, delimiter);

        double[] doubleValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            doubleValues[i] = Double.parseDouble(values[i]);
        }
        return doubleValues;
    }

    public static short[] shortArray(String value) {
        return shortArray(value, DELIMITER);
    }

    private static short[] shortArray(String value, String delimiter) {
        if (!notEmpty(value)) {
            return null;
        }
        String[] values = StringUtils.split(value, delimiter);

        short[] shortValues = new short[values.length];
        for (int i = 0; i < values.length; i++) {
            shortValues[i] = Short.parseShort(values[i]);
        }
        return shortValues;
    }

    public static long[] longArray(String value) {
        return longArray(value, DELIMITER);
    }

    public static long[] longArray(String value, char delimiter) {
        if (!notEmpty(value)) {
            return null;
        }
        String[] values = StringUtils.split(value, delimiter);

        long[] longValues = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Long.parseLong(values[i]);
        }
        return longValues;
    }

    private static long[] longArray(String value, String delimiter) {
        if (!notEmpty(value)) {
            return null;
        }
        String[] values = StringUtils.split(value, delimiter);

        long[] longValues = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            if (notEmpty(values[i])) {
                longValues[i] = Long.parseLong(values[i].trim());
            }
        }
        return longValues;
    }

    public static Integer[] integerArray(String value, String delimiter) {
        if (!notEmpty(value)) {
            return null;
        }
        String[] values = StringUtils.split(value, delimiter);

        Integer[] integerValues = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            if (notEmpty(values[i])) {
                integerValues[i] = Integer.valueOf(values[i].trim());
            }
        }
        return integerValues;
    }

    public static List<String> stringList(String value, String delimiter) {
        if (!notEmpty(value)) {
            return null;
        }
        String[] values = StringUtils.split(value, delimiter);

        List<String> listValues = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            listValues.add(i, values[i]);
        }
        return listValues;
    }

    public static List<long[]> grouping(long[] values, int groupSize) {
        if (notEmpty(values)) {

            Assertion.isPositive(groupSize, "divideSize must be bigger than 0");

            int groupLength = values.length / groupSize + ((values.length % groupSize) > 0 ? 1 : 0);

            List<long[]> longArryGroup = new LinkedList<>();
            long[] valueArray;
            for (int i = 0; i < groupLength; i++) {
                int arrayLength = (i < groupLength - 1 || values.length % groupSize == 0) ? groupSize : (values.length % groupSize);

                valueArray = new long[arrayLength];
                System.arraycopy(values, i * groupSize, valueArray, 0, arrayLength);
                longArryGroup.add(valueArray);
            }

            return longArryGroup;
        }
        return null;
    }

    public static <T> List<List<T>> grouping(List<T> values, int groupSize) {
        if (values != null && values.size() > 0) {

            Assertion.isPositive(groupSize, "divideSize must be bigger than 0");

            int groupLength = values.size() / groupSize + ((values.size() % groupSize) > 0 ? 1 : 0);

            List<List<T>> longArryGroup = new LinkedList<>();
            for (int i = 0; i < groupLength; i++) {
                int arrayLength = (i < groupLength - 1 || values.size() % groupSize == 0) ? groupSize : (values.size() % groupSize);

                List<T> valueArray = new ArrayList<>();
                for (int j = 0; j < arrayLength; j++) {
                    valueArray.add(values.get(i * groupSize + j));
                }
                longArryGroup.add(valueArray);
            }

            return longArryGroup;
        }
        return null;
    }


    public static String join(String[] values) {
        return StringUtils.join(values, DELIMITER);
    }

    public static List<?> asList(Object object) {
        if (object instanceof List<?>) {
            return (List<?>) object;
        }
        return null;
    }

    public static String asString(Object object) {
        if (null != object) {
            return StringUtils.trim(String.valueOf(object));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] asArray(List<E> list) {
        if (notEmpty(list)) {
            return (E[]) list.toArray();
        }
        return null;
    }

    public static boolean equals(String left, String right) {
        if (null == left && null == right) {
            return true;
        }
        return null != left && left.equals(right);
    }

    public static String trim(String string) {
        if (null == string) {
            return null;
        }

        return StringUtils.trim(string);
    }

    public static int getByteLength(String string) {
        if (StringUtils.isEmpty(string)) {
            return 0;
        }
        try {
            return string.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("GBK charset not supported", e);
        }
    }

    public static String trimString(String string, int byteLength) {
        try {
            String charset = "GBK";
            if (notEmpty(string) && string.getBytes(charset).length > byteLength) {
                byte[] bytes = string.getBytes(charset);
                bytes = ArrayUtils.subarray(bytes, 0, byteLength);
                return new String(bytes, charset);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("GBK charset not supported", e);
        }
        return string;
    }

    /**
     * escape
     */
    public static long[] escapeDuplication(long[] values) {
        if (Detect.notEmpty(values)) {
            Set<Long> set = new HashSet<>(Arrays.asList(ArrayUtils.toObject(values)));
            return ArrayUtils.toPrimitive(set.toArray(new Long[0]));
        }
        return null;
    }

    public static <E> List<E> escapeEmpty(List<E> list) {
        if (notEmpty(list)) {
            return list;
        }
        return null;
    }

    public static String escapeVarchar(String value) {
        int limit = 2000;
        if (notEmpty(value) && value.length() > limit) {
            return value.substring(0, 1990);
        }
        return value;
    }

    public static short[] safeArray(short[] values) {
        if (null == values) {
            values = new short[0];
        }
        return values;
    }

    public static int[] safeArray(int[] values) {
        if (null == values) {
            values = new int[0];
        }
        return values;
    }

    public static long[] safeArray(long[] values) {
        if (null == values) {
            values = new long[0];
        }
        return values;
    }

    public static double[] safeArray(double[] values) {
        if (null == values) {
            values = new double[0];
        }
        return values;
    }

    public static <E> List<E> safeList(List<E> list) {
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static <E> E firstOne(List<E> list) {
        if (notEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public static <E> E lastOne(List<E> list) {
        if (notEmpty(list)) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    public static boolean onlyOne(List<?> list) {
        return notEmpty(list) && list.size() == 1;
    }

    public static <E> List<E> unmodifiableList(List<E> list) {
        if (notEmpty(list)) {
            return Collections.unmodifiableList(list);
        }
        return null;
    }

    public static boolean between(long value, long floor, long ceil) {
        return value >= floor && value <= ceil;
    }

    public static void notNull(Object obj, String message) {
        if (null == obj) {
            throw new IllegalArgumentException(message);
        }
    }

    public static String changeSpecialChar(String s) {
        s = s.replace("\\", "\\\\");
        s = s.replace("\r", "\\r");
        s = s.replace("\n", "\\n");
        s = s.replace("\"", "\\\"");
        s = s.replace("\'", "\\\'");
        return s;
    }

    public static String replaceSpecialCharForCatSearch(String s) {
        for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            boolean b = c > 65280 && c < 65375;
            if (!Character.isLetterOrDigit(c) && c != 12288 && !b) {
                // 12288是全角空格
                // 65281是！, 65374是～，之间都是全角符号
                s = StringUtils.replace(s, Character.toString(c), " ");
            }
        }
        return s;
    }

    /**
     * 随机生成指定位数的整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(final int min, final int max) {
        Random rand;
        rand = new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;
    }

    /**
     * 随机生成指定位数的整数
     *
     * @param min
     * @param max
     * @param length >= 1
     * @return
     */
    private static String randomNumber(final int min, final int max, final int length) {
        Assertion.isTrue(length > 0, "length must > 0 !");
        Random rand;
        rand = new Random();
        float tmp = rand.nextFloat();
        long len = Long.valueOf(StringUtils.rightPad("1", length + 1, '0'));
        return String.valueOf((int) (tmp * len));
    }

    public static String transcodingValue(String str) {
        if (notEmpty(str)) {
            str = str.replaceAll("Y", "年").replaceAll("M", "月").replaceAll("D", "天");
        }
        return str;
    }

    public static String transcodingAgeValue(String str) {
        if (notEmpty(str)) {
            str = str.replaceAll("D", "天").replaceAll("S", "周岁");
        }
        return str;
    }

    public static String asChinaValue(String str) {
        String y = "Y";
        String m = "M";
        String d = "D";
        if (notEmpty(str)) {
            if (str.toUpperCase().endsWith(y)) {
                return "年";
            } else if (str.toUpperCase().endsWith(m)) {
                return "月";
            } else if (str.toUpperCase().endsWith(d)) {
                return "天";
            }
        }
        return str;
    }

}
