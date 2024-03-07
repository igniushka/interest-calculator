import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    static Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }
    static long differenceInDays(Date startDate, Date endDate){
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}
