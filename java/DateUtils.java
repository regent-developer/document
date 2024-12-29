package com.demo;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 提供了常用的日期操作方法，例如获取某日期的开始和结束时间，计算两个日期之间的天数等。
 */
@Slf4j
public class DateUtils {

    // 常用日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String MINUTE_FORMAT = "HH:mm";

    public DateUtils() {
    }

    /**
     * 获取指定日期当天的开始时间
     *
     * @param calendar 日历对象
     * @return 当天的开始时间
     */
    public static Date getStartDate(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期当天的结束时间
     *
     * @param calendar 日历对象
     * @return 当天的结束时间
     */
    public static Date getEndDate(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 在指定日期上加上或减去指定天数
     *
     * @param date 起始日期
     * @param day  要增加的天数（负数表示减去）
     * @return 新的日期
     */
    public static Date addDate(Date date, int day) {
        long millis = date.getTime() + day * 24L * 3600L * 1000L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 获取指定日期当天的开始时间
     *
     * @param date 日期对象
     * @return 当天的开始时间
     */
    public static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期当天的结束时间
     *
     * @param date 日期对象
     * @return 当天的结束时间
     */
    public static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间的周数差
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 周数差
     */
    public static int weeksOfTwoDates(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int i = 0;
        while (!endDate.before(startDate)) {
            i++;
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            endDate = calendar.getTime();
        }
        return i;
    }

    /**
     * 获取当前周的周一日期
     *
     * @return 当前周的周一日期
     */
    public static Date getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DAY_OF_MONTH, mondayPlus);
        return currentDate.getTime();
    }

    /**
     * 获取上周日的日期
     *
     * @return 上周日的日期
     */
    public static Date getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DAY_OF_MONTH, mondayPlus + 6);
        return currentDate.getTime();
    }

    /**
     * 获取当前日期到最近的周一的天数差
     *
     * @return 天数差
     */
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == 1 ? -6 : 2 - dayOfWeek;
    }

    /**
     * 获取当前月的第一天
     *
     * @return 当前月的第一天
     */
    public static Calendar getMinMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, calendar.getActualMinimum(5));
        return calendar;
    }

    /**
     * 获取当前月的最后一天
     *
     * @return 当前月的最后一天
     */
    public static Calendar getMaxMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    /**
     * 获取上周一的日期
     *
     * @return 上周一的日期
     */
    public static Calendar getLastMonday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, offset - 7);
        return calendar;
    }

    /**
     * 获取上周日的日期
     *
     * @return 上周日的日期
     */
    public static Calendar getLastSunday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = Calendar.DAY_OF_WEEK - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, offset - 7);
        return calendar;
    }

    /**
     * 获取上个月的第一天
     *
     * @return 上个月的第一天
     */
    public static Calendar getLastMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    /**
     * 获取上个月的最后一天
     *
     * @return 上个月的最后一天
     */
    public static Calendar getLastMonthEndDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar;
    }

    /**
     * 将日期对象格式化为字符串
     *
     * @param date 日期对象
     * @return 格式化后的字符串
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return date != null ? sdf.format(date) : "";
    }

    /**
     * 获取指定日期之前的若干天
     *
     * @param date 日期对象
     * @param num  天数
     * @return 指定日期之前的若干天
     */
    public static Date getPreDateStr(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -num);
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 获取当前周的周一日期
     *
     * @return 当前周的周一日期
     */
    public static Date getMondayStrOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -6);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -(dayWeek - 2));
        }
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 判断指定日期是否为当月的最后一天
     *
     * @param date 日期对象
     * @return true 表示是当月的最后一天，否则不是
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = calendar.get(Calendar.DAY_OF_MONTH);
        return now == lastDay;
    }
    /**
     * 计算两个日期之间的天数
     *
     * @param startDateStr 开始日期字符串
     * @param endDateStr   结束日期字符串
     * @return 两个日期之间的天数
     * @throws Exception 日期解析异常
     */
    public static int daysOfTwo(String startDateStr, String endDateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000L / 60L / 60L / 24L);
    }

    /**
     * 获取最近12天的日期字符串列表
     *
     * @return 最近12天的日期字符串列表
     */
    public static List<String> getContinue12Days() {
        List<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -11);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        for (int i = 0; i <= 11; ++i) {
            Date time = calendar.getTime();
            list.add(sdf.format(time));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    /**
     * 获取指定日期的年份周次字符串
     *
     * @param dateStr 日期字符串
     * @return 年份周次字符串
     * @throws Exception 日期解析异常
     */
    public static String getWeeksOfYear(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.WEEK_OF_YEAR);
        if (i >= 10) {
            return dateStr.substring(2, 4) + "-" + i;
        } else {
            return dateStr.substring(2, 4) + "-0" + i;
        }
    }

    /**
     * 将日期字符串解析为日期对象
     *
     * @param dateStr 日期字符串
     * @return 日期对象
     * @throws Exception 日期解析异常
     */
    public static Date getDateByStr(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期之间的天数
     */
    public static int daysOfTwoDates(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000L / 60L / 60L / 24L);
    }

    /**
     * 获取指定日期的月份第一天
     *
     * @param date 日期对象
     * @return 月份第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定年份和月份的最后一天
     *
     * @param year  年份
     * @param month 月份
     * @return 月份最后一天
     */
    public static final Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return calendar.getTime();
    }

    /**
     * 判断指定日期是否是今天
     *
     * @param date 日期对象
     * @return true 表示是今天，否则不是
     */
    public static boolean isNow(Date date) {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
        String nowDay = sf.format(now);
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 将日期对象格式化为指定格式的字符串
     *
     * @param date   日期对象
     * @param format 格式字符串
     * @return 格式化后的日期字符串
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将字符串解析为指定格式的日期对象
     *
     * @param str    日期字符串
     * @param format 格式字符串
     * @return 日期对象
     * @throws Exception 日期解析异常
     */
    public static Date parse(String str, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    /**
     * 判断当前时间是否在指定时间范围内
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 表示在范围内，否则不在范围内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        return date.after(begin) && date.before(end);
    }

    /**
     * 将时间戳格式化为指定格式的日期字符串
     *
     * @param dateTime 时间戳
     * @param format   格式字符串
     * @return 格式化后的日期字符串
     */
    public static String formatLongToDateStr(Long dateTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(dateTime));
    }
}
