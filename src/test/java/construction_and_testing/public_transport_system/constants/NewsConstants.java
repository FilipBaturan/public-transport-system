package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.domain.Operator;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;


public class NewsConstants {

    public static final Long DB_ID = 1L;
    public static final Long DB_ID_2 = 2L;
    public static final Long DB_INVALID_ID = 12L;

    public static final String DB_TITLE = "Title1";
    public static final String DB_CONTENT = "Content1";
    public static final String DB_CHANGED_TITLE = "Title changed";
    public static final String DB_CHANGED_CONTENT = "Content changed";


    public static final int DB_COUNT = 5;

    public static final LocalDateTime DB_TIME_1 = LocalDateTime.of(2019, 1, 15, 13, 45);
    public static final LocalDateTime DB_TIME_2 = LocalDateTime.of(2019, 1, 14, 3, 27);
    public static final LocalDateTime DB_TIME_3 = LocalDateTime.of(2019, 1, 16, 23, 35);
    public static final LocalDateTime DB_TIME_NEW = LocalDateTime.of(2019, 1, 19, 20, 35);
    public static final LocalDateTime DB_CHANGED_TIME = LocalDateTime.of(2019, 1, 20, 20, 35);

    public static final News DB_NEWS_1 = new News(1L,"Title1", "Content1",DB_TIME_1, new Operator(3L));
    public static final News DB_NEWS_2 = new News(2L,"Title2", "Content2",DB_TIME_2, new Operator(3L));
    public static final News DB_NEWS_3 = new News(3L,"Title3", "Content3",DB_TIME_3, new Operator(3L));
    public static final News DB_NEW_NEWS = new News(4L,"Title new", "Content new",DB_TIME_NEW, new Operator(3L));
    public static final News DB_CHANGED_NEWS = new News(2L,DB_CHANGED_TITLE, DB_CHANGED_CONTENT,DB_CHANGED_TIME, new Operator(3L));
    public static final News DB_CHANGED_INVALID_NEWS = new News(DB_INVALID_ID, "Title1", "Content1",DB_TIME_1, new Operator(3L));
    public static final News DB_DELETED_NEWS = new News(1L,"Title1", "Content1",DB_TIME_1, new Operator(3L), false);

    public static boolean isSorted(List<News> news){
        LocalDateTime date = news.get(0).getDate();
        news.remove(0);
        for(News n : news){
            if(n.getDate().isBefore(date)){
                date = n.getDate();
            }
            else {
                return false;
            }
        }
        return true;
    }

}
