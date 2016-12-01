package com.example.journey.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  public static boolean isMobileNumber(String mobiles) {
    Pattern p = Pattern.compile("^1[0-9]{10}$");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }

  public static String formatTime(String string) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    Date date = null;
    try {
      date = format.parse(string);
    } catch (ParseException e) {
      Log.i("formatTime", e.getMessage());
    }
    if (date != null) {
      Date now = Calendar.getInstance().getTime();
      if (date.getYear() == now.getYear()) {
        if (date.getMonth() == now.getMonth() && date.getDay() == now.getDay()) {
          DateFormat df = new SimpleDateFormat("HH:mm", Locale.CHINA);
          return df.format(date);
        } else {
          DateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
          return df.format(date);
        }
      } else {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return df.format(date);
      }
    }
    return string.substring(0, string.length() - 3);
  }

  public static String formatTimeOnlyDate(String string) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    Date date = null;
    try {
      date = format.parse(string);
    } catch (ParseException e) {
      Log.i("formatTime", e.getMessage());
    }
    if (date != null) {
      Date now = Calendar.getInstance().getTime();
      if (date.getYear() == now.getYear()) {
        if (date.getMonth() == now.getMonth() && date.getDay() == now.getDay()) {
          return "今天";
        } else {
          DateFormat df = new SimpleDateFormat("MM-dd", Locale.CHINA);
          return df.format(date);
        }
      } else {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df.format(date);
      }
    }
    return string.substring(0, string.length() - 9);
  }

  public static String formatDates(String startDate, String endDate) {
    String string = "";
    string += startDate.replace("-", ".");
    string += " - ";
    string += endDate.replace("-", ".");
    return string;
  }

  public static String formatLocation(List<String> provinces, List<String> cities) {

    if (provinces.size() != cities.size()) {
      return "地点有误";
    }

    String string = "";
    for (int i = 0; i < provinces.size(); i++) {
      string += provinces.get(i);
      string += " · ";
      string += cities.get(i);

      if (i != provinces.size() - 1) string += ", ";
    }

    return string;
  }

}
