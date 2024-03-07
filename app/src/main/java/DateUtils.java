import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

    static Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }
    static long differenceInDays(Date startDate, Date endDate){
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    static void incrementDay(Date date) {
        date.setTime(date.getTime() + (1000 * 60 * 60 * 24));
    }

    static String formattedDate(Date date){
     return  sdf.format(date);
    }

}
