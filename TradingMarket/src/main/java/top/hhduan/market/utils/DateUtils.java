package top.hhduan.market.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author duanhaohao
 */
@Slf4j
public class DateUtils {
    private final static String DEFAULT_FORMAT = "yyyy-MM-dd";

    private final static String TIME_PATTERN = "HH:mm";

    private final static String TIME_PATTERN2 = "yyyyMMddHHmmss";

    private final static String TIME_PATTERN3 = "yyyyMMddHHmmssSSS";

    private final static String TIME_PATTERN4 = "yyyyMMdd";

    private final static String TIME_PATTERN5 = "yyyy-MM";

    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static Date parseStrToDate(String time) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date date = null;
        try {
             date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据日期格式，返回日期按DEFAULT_FORMAT格式转换后的字符串
     *
     * @param date 日期对象
     * @return 格式化后的日期的页面显示字符串
     */
    public static String getTimeString(Date date) {
        SimpleDateFormat df;
        String returnValue = "";

        if (date != null) {
            df = new SimpleDateFormat(TIME_PATTERN4);
            returnValue = df.format(date);
        }

        return (returnValue);
    }

    /**
     * 根据日期格式，返回日期按DEFAULT_FORMAT格式转换后的字符串
     *
     * @param aDate 日期对象
     * @return 格式化后的日期的页面显示字符串
     */
    public static String getDateString(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(TIME_PATTERN3);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }


    /**
     * 根据日期格式，返回日期按DEFAULT_FORMAT格式转换后的字符串
     *
     * @param aDate 日期对象
     * @return 格式化后的日期的页面显示字符串
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(TIME_PATTERN2);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static String parseToDateTimeStr(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(DATE_TIME_PATTERN);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            // smdate
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            // bdate
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException e) {
            throw new RuntimeException("daysBetween error" + smdate + ";" + bdate, e);
        }
    }


    /**
     * 计算两个日期之间相差的月数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差月数
     */
    public static int getMonthSpace(Date smdate, Date bdate) {
        return Integer.parseInt(DurationFormatUtils.formatPeriod(smdate.getTime(), bdate.getTime(), "M"));
    }

    public static int getMonth(Date smdate, Date bdate) {

        int result;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(smdate);
        c2.setTime(bdate);

        result = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);

        return result == 0 ? 1 : Math.abs(result);

    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param aMask   输入字符串的格式
     * @param strDate 一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     * @throws ParseException
     * @see SimpleDateFormat
     */
    private static Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: yyyy/MM/dd HH:MM
     * a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: yyyy-MM-dd
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see SimpleDateFormat
     */
    private static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 根据日期格式，返回日期按DEFAULT_FORMAT格式转换后的字符串
     *
     * @param aDate
     * @return
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(DEFAULT_FORMAT, aDate);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate (格式 yyyy-MM-dd)
     * @return
     * @throws ParseException
     */
    private static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + DEFAULT_FORMAT);
            }

            aDate = convertStringToDate(DEFAULT_FORMAT, strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");

            throw new ParseException(pe.getMessage(), pe.getErrorOffset());

        }

        return aDate;
    }

    /**
     * 时间相加
     *
     * @param date
     * @param day
     * @return
     */
    private static Date addDay(Date date, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static String addMinute(Date date, int minute) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return DateUtils.parseToDateTimeStr(calendar.getTime());
    }

    /**
     * 月相加
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static int getDay(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 本年的第一天
     */
    public static String getYearFirst(Integer year) {
        return formatDate(getCurrYearFirst(year));
    }

    /**
     * 本年的最后一天
     *
     * @param year
     * @return getYearLast
     * @author cjx 2013-2-25 DateUtil String
     */
    public static String getYearLast(Integer year) {
        return formatDate(getCurrYearLast(year));
    }

    public static Integer getYear() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        String year = f.format(date);
        return Integer.valueOf(year);

    }

    /**
     * return yyyy-MM-dd
     *
     * @param date
     * @return
     */
    private static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        return f.format(date);
    }

    public static String formatDate(String formatPattern, Date date) {
        SimpleDateFormat f = new SimpleDateFormat(formatPattern);
        return f.format(date);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    private static Date getCurrYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 只保留时间中的年月日
     *
     * @param d
     * @return
     */
    public static Date preciseToDay(Date d) {
        //yyyy-MM-dd 格式转换成字符串
        String day = getDateTime(DEFAULT_FORMAT, d);
        try {
            return convertStringToDate(DEFAULT_FORMAT, day);
        } catch (Exception e) {

            return null;
        }
    }


    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    private static Date getCurrYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    public static String getLastDay() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //此时打印它获取的是系统当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        System.out.println(s);

        return s;

    }

    /**
     * 获取本月日期
     *
     * @return gdl
     */
    public static String[] findMonthDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String startDate = format.format(calendar.getTime());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        String endDate = format.format(calendar.getTime());
        return new String[]{startDate, endDate};
    }

    /**
     * 得到上月1号到月底日期
     *
     * @return String[]
     * 2014-7-24
     * fx
     */
    public static String[] findLastMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String dayFirstPrev = df.format(gcLast.getTime());
        dayFirstPrev = dayFirstPrev + " 00:00:00";

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        String dayEndPrev = df.format(calendar.getTime());
        dayEndPrev = dayEndPrev + " 23:59:59";

        return new String[]{dayFirstPrev, dayEndPrev};
    }

    /**
     * 得到上周一到周日日期
     *
     * @return String[] 0 上周一 1 上周日
     * gdl
     */
    public static String[] getLastWeekDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] day = new String[2];
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        day[0] = sdf.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, 2);
        day[1] = sdf.format(cal.getTime());
        return day;
    }

    /**
     * 获取上一周的时间
     *
     * @param date
     * @return
     */
    public static String[] getNewLastWeekDay(Date date) {
        String[] day = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        //nd为传过来的日期，Date 型，此步可省为当前日期
        cl.setTime(date);
        int dayOfWeek = cl.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        //idx 参数，0为当前，1为下周 -1为上周以此类推
        cl.add(Calendar.WEEK_OF_MONTH, -1);
        cl.add(Calendar.DATE, -dayOfWeek + 1);
        //周一
        day[0] = sdf.format(cl.getTime());
        cl.add(Calendar.DATE, +6);
        //周日
        day[1] = sdf.format(cl.getTime());
        return day;
    }

    /**
     * 得到本周一到周日日期
     *
     * @return String[] 0 本周一 1 本周日
     * 2014-7-24
     * fx
     */
    public static String[] getWeekDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] day = new String[2];
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        c.add(Calendar.DATE, -dayofweek + 1);
        day[0] = sdf.format(c.getTime());
        int dayweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayweek == 0) {
            dayweek = 7;
        }
        c.add(Calendar.DATE, -dayweek + 7);
        day[1] = sdf.format(c.getTime());

        return day;
    }

    /**
     * 计算连续天数
     *
     * @param days
     * @return
     */
    public static int calContinueDays(List<Date> days) {
        int continueDay = 0;
        Date lDay = null;
        for (Date day : days) {
            if (lDay != null) {
                if (!isContinueDay(day, lDay)) {
                    return continueDay;
                }
            }
            lDay = day;
            continueDay++;
        }
        return continueDay;
    }

    private static boolean isContinueDay(Date preDay, Date curDay) {
        String d1 = formatDate(addDay(preDay, 1));
        String d2 = formatDate(curDay);

        return d1.equals(d2);
    }

    public static int compareDate(Date date1, Date date2) {

        try {
            return Long.compare(date1.getTime(), date2.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getSystemTime() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        return df.format(new Date());
    }

    /**
     * 获取月份的第一天
     *
     * @param amount
     * @return
     */
    public static String getFirstDayOfMonth(int amount) {
        Calendar calendar = Calendar.getInstance();
        if (amount != 0) {
            calendar.add(Calendar.MONTH, amount);
        }
        SimpleDateFormat format = new SimpleDateFormat(TIME_PATTERN5);
        return format.format(calendar.getTime()) + "-01";
    }

    /**
     * 计算当前时间到24点前的时间差值
     */
    public static int timeDiffToTwentyFour() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date t1 = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Date t2 = calendar.getTime();
        return Math.toIntExact((t2.getTime() - t1.getTime()) / 1000);
    }

    /**
     * 获取n天的日期
     *
     * @param n(正数:后n天;负数:前n天;0:当前日期)
     * @return
     */
    public static Date getNDate(Integer n) {
        //得到一个Calendar的实例
        Calendar ca = Calendar.getInstance();
        //设置时间为当前时间
        ca.setTime(new Date());
        //前n天
        ca.add(Calendar.DATE, n);
        //结果
        return ca.getTime();
    }

    /**
     * return yyyy-MM-01
     * 获取当月日期第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDateConvertStr(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(TIME_PATTERN5);
        String sDate = f.format(date);
        return sDate + "-01";
    }

    public static String getTimePatternDate(Date aDate, String timePattern) {
        SimpleDateFormat df;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(timePattern);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    public static Long getTimestamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String getYYYYMMDDHHMMSS(Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date date = new Date(timestamp);
        return sdf.format(date);
    }


    /**
     * 获取nDate天前0点时间戳
     *
     * @param nDate (正数:后n天;负数:前n天;0:当前日期)
     * @return
     */
    public static Long dayTimeInMillis(Integer nDate) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, nDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

}
