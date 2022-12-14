package com.argea.argeaportal.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public class StringUtils {

    public static final String EMPTY = "";

    public static String nullToEmpty(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    public static String trimNullToEmpty(String s) {
        if (s == null) {
            return "";
        } else {
            return s.trim();
        }
    }

    public static String trimNullToEmpty(Integer s) {
        if (s == null) {
            return "";
        } else {
            return s.toString();
        }
    }

    public static String trimEmptyToNull(String s) {
        if (s == null) {
            return null;
        } else {
            if (s.trim().equals(EMPTY)) {
                return null;
            } else {
                return s.trim();
            }
        }
    }

    public static String nullToEmpty(Integer s) {
        if (s == null) {
            return "";
        } else {
            return s.toString();
        }
    }

    public static String nullToEmpty(Double s) {
        if (s == null) {
            return "";
        } else {
            return s.toString();
        }
    }

    public static int getPositionOfNthOccurrence(String str, String substr, int n) {
        n--;
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    public static String replaceNthOccurrence(String str,String substr, String replaceWith, int n){
        int position=getPositionOfNthOccurrence(str,substr,n);
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.replace(position, position+substr.length(), replaceWith);
        return stringBuilder.toString();
    }

    public static String  formatBigDecimalToLocaleOnlySignificativeDigits(BigDecimal bigDecimal, Optional<Locale> locale, Optional<Integer> minimumDigits){
        int significativeDigits = minimumDigits.orElse(0);

        if (bigDecimal == null) {
            return "";
        }

        int d = bigDecimal.stripTrailingZeros().scale();
        significativeDigits = ((d > significativeDigits) ? d : significativeDigits);

        String repeated = "";
        for (int i = 0; i < significativeDigits; i++) {
            repeated = repeated + "0";
        }

        //locale italiano se non specificato
        DecimalFormatSymbols decimalFormatSymbols  = DecimalFormatSymbols.getInstance(new Locale(locale.orElse(Locale.ITALY).getLanguage(), locale.orElse(Locale.ITALY).getCountry()));

        String strFormato = "#" +((significativeDigits>0)?".":"") + repeated;
        final String formattedValue = new DecimalFormat(strFormato, decimalFormatSymbols).format(bigDecimal);
        return formattedValue;
    }

    public static boolean equalsNullSafe(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if ((a == null && b != null) || (a != null && b == null)) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    public static String removeNonPrintableCharachters(String text)
    {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("\\p{Cntrl}&&[^\r\n\t]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }
}
