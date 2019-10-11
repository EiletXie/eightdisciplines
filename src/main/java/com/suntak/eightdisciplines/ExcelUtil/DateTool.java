package com.suntak.eightdisciplines.ExcelUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
    public static Date parse(String string) {
        try {
            DateFormat format = DateFormat.getDateInstance();
            return format.parse(string);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String getTodayTime() {
        Date sellTime = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(sellTime);
        System.out.println(cal.toString());
        DateFormat format = DateFormat.getDateInstance();

        System.out.println(format.format(sellTime));
        return null;
    }

    public static String getTodayDate() {
        Date sellTime = new Date();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
        return dateformat1.format(sellTime);
    }

    public static String getMonth_day(Date date, String str) {
        if (str == null)
            str = "/";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return (cal.get(Calendar.MONTH) + 1) + str + cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String getTodayDateLong() {
        Date sellTime = new Date();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat1.format(sellTime);
    }

    public static int currentYear() {

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        // int month = cal.get(Calendar.MONTH )+1;

        // System.out.println(year + " �� " + month + " ��");

        return year;
    }

    public static String format(Date date, String arg) {
        if (date == null)
            return null;
        if (arg == null)
            arg = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(arg);
        return sdf.format(date);
    }

    public static Date stringToDate(String str) throws ParseException {
        if (str.split("-").length == 2) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            return sdf.parse(str);
        } else if(str.split("-").length == 1){ 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            return sdf.parse(str);
        } else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(str);
        }

    }

    public static int countDays(String begin,String end){
          int days = 0;
          
          DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
          Calendar c_b = Calendar.getInstance();
          Calendar c_e = Calendar.getInstance();
          
          try{
           c_b.setTime(df.parse(begin));
           c_e.setTime(df.parse(end));
           
           while(c_b.before(c_e)){
            days++;
            c_b.add(Calendar.DAY_OF_YEAR, 1);
           }
           
          }catch(ParseException pe){
           System.out.println("���ڸ�ʽ����Ϊ��yyyy-MM-dd���磺2010-4-4.");
          }
          
          return days; 
        } 
    
    public static String addCalendarDay(Date calDate, long addDate) {
        long time = calDate.getTime();
        addDate = addDate * 24 * 60 * 60 * 1000;
        time += addDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(time));
    }
    
    public static Date addDay(Date calDate, long addDate) {
        long time = calDate.getTime();
        addDate = addDate * 24 * 60 * 60 * 1000;
        time += addDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Date(time);
    }
    
    public static Date reduceDay(Date calDate, long addDate) {
        long time = calDate.getTime();
        addDate = addDate * 24 * 60 * 60 * 1000;
        time -= addDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Date(time);
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // DateTool.getTodayTime();
        // DateTool.currentYear();

        try {
            //System.out.println(DateTool.stringToDate("2001-10-10"));
            Date date=new Date();
            System.out.println(DateTool.addCalendarDay(date,1));
            //System.out.println(DateTool.countDays("2012-4-10","2012-4-24"));
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
