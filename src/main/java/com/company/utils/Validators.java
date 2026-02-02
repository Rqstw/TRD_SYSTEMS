package com.company.utils;
import java.math.BigDecimal;
public class Validators {
    public static String nonEmpty(String s) {
        if (s == null) return "";
        return s.trim();
    }
    public static int positiveInt(String s) {
        int v = Integer.parseInt(s);
        if (v <= 0) throw new IllegalArgumentException("must be > 0");
        return v;
    }
    public static BigDecimal money(String s) {
        BigDecimal v = new BigDecimal(s);
        if (v.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("money < 0");
        return v;
    }
}
