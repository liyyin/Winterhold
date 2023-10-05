package com.Winterhold;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Helper {

    public static String moneyFormater(BigDecimal price){
        Locale indo = new Locale("id","ID");
        String hasil = NumberFormat.getNumberInstance(indo).format(price);
        return hasil;
    }
    public static String formatTanggal(LocalDate date, String pattern){
        Locale indo = new Locale("id","ID");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern,indo);
        String result = date.format(formatter);
        return result;
    }
}
