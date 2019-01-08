package construction_and_testing.public_transport_system.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeStringComparator implements Comparator<String>{
    private DateFormat primaryFormat = new SimpleDateFormat("HH:mm");

    @Override
    public int compare(String time1, String time2){
        return timeInMillis(time1) - timeInMillis(time2);
    }

    public int timeInMillis(String time){
        return timeInMillis(time, primaryFormat);
    }

    private int timeInMillis(String time, DateFormat format) {
        try{
            Date date = format.parse(time);
            return (int)date.getTime();
        }catch(ParseException e) {
            if (time.equals(""))
                return Integer.MAX_VALUE;
            e.printStackTrace();
        }
        return 0;
    }

    /**public static void main(String[] args){
        List<String> times = Arrays.asList(new String[]{"", "22:30", "08:30", "", "16:15"});
        Collections.sort(times, new TimeStringComparator());
        System.out.println(times);
    }**/

}