package com.automatodev.covids.view;
import java.text.NumberFormat;
import java.util.Locale;
public class FormatUtils {

    public String decimal(long value){
        Locale locale = new Locale("pt","br");
        NumberFormat f = NumberFormat.getInstance(locale);

        return f.format(value);
    }

    public String decimal(float value){
        Locale locale = new Locale("pt","br");
        NumberFormat f = NumberFormat.getInstance(locale);

        return f.format(value);
    }
}
