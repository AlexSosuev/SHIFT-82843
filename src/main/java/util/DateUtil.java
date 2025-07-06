package util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {
    public static String unixToIso(long unixTime, String timezone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(unixTime),
                ZoneId.of(timezone)).toString();
    }
}
