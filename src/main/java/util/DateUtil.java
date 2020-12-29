package util;


import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {

    public static Timestamp d2t(Date d) {
        if(null == d)
            return null;
        return new Timestamp(d.getTime());
    }

    public static Date t2d(Timestamp t) {
        if(null == t)
            return null;
        return new Date(t.getTime());
    }
}
