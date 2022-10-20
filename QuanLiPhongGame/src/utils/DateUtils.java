package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String pattern = "dd-MM-yyyy";
    public static String convertInstantToStringFormat(Instant date){
        if(date!=null){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());;
            return dateTimeFormatter.format(date);
        }else {
            return "";
        }

    }
}