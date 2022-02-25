package com.hsbc.qe.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final String dateFormat_YYYYMMDD = "yyyy-MM-dd";
    private static final TimeZone timeZone = TimeZone.getTimeZone("UTC");

    public static String getTodayDateInString() throws ParseException {
        LocalDate date = LocalDate.now(ZoneOffset.UTC);
        SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat_YYYYMMDD);
        dtFormat.setTimeZone(timeZone);
        return (dtFormat.format((dtFormat.parse(String.valueOf(date)))));
    }

    public static String getDate(int noOfDays, String dateFormatPattern) {
        String selectedDate;
        SimpleDateFormat dmyFormat = new SimpleDateFormat(dateFormatPattern);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, noOfDays);
        date = cal.getTime();
        selectedDate = dmyFormat.format(date);
        return selectedDate;
    }

    public static String getTodayDate(String dateFormatPattern) {
        return getDate(0, dateFormatPattern);
    }

    public static Date convertStringToDate(String date, String dateFormat) {
        SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);
        dtFormat.setTimeZone(timeZone);
        Date returnDate = null;
        try {
            returnDate = StringUtils.isNotBlank(date) ? dtFormat.parse(date) : null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    public static Date addMonthToDate(Date date, int noOfMonths) {
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime(date);
        cal.add(Calendar.MONTH, noOfMonths);
        return cal.getTime();
    }

    public static boolean isSameDate(Date d1, Date d2) {
        Calendar date1 = Calendar.getInstance(timeZone);
        Calendar date2 = Calendar.getInstance(timeZone);
        date1.setTime(d1);
        date2.setTime(d2);
        try {
            if (date1 != null && date2 != null) {
                if (date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                        && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)
                        && date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) {
                    return true;

                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Date convertStringToDateFormat(String date) {
        return convertStringToDate(date, dateFormat_YYYYMMDD);
    }

    public static boolean compareDates(String dt1, String dt2) {
        Date d1 = convertStringToDate(dt1, dateFormat_YYYYMMDD);
        Date d2 = convertStringToDate(dt2, dateFormat_YYYYMMDD);
        Calendar date1 = Calendar.getInstance(timeZone);
        Calendar date2 = Calendar.getInstance(timeZone);
        date1.setTime(d1);
        date2.setTime(d2);
        try {
            if (date1 != null && date2 != null) {
                if(date1.after(date2)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
