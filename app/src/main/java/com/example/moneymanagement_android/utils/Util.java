package com.example.moneymanagement_android.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String formatCurrency(long price) {
        NumberFormat formatter = new DecimalFormat("#,### đ");
        String formattedNumber = formatter.format(price);
        return formattedNumber;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = sdf.format(date);
        sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(date);
        return day + " tháng " + month + " " + year;
    }
}
