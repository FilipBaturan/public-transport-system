package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScheduleConstants {

    public static final int DB_COUNT = 7;

    public static final Long DB_VALID_ID = 100L;
    public static final Long DB_TL_ID = 1L;
    public static final DayOfWeek DB_VALID_DAY_OF_WEEK = DayOfWeek.WORKDAY;
    public static final Integer DB_VALID_DEPARTURES_SIZE = 5;
    public static final boolean DB_VALID_ACTIVE = true;

    public static final ArrayList<String> DB_VALID_DEPARTURES = new ArrayList<String>() {
        {
            add("08:00");
            add("08:15");
            add("08:30");
            add("08:45");
            add("09:00");
        }
    };


    public static final Long DB_INVALID_ID = 1000L;

    public static final Long DB_NEW_ID = 100L;
    public static final Long DB_NEW_TL_ID = 1L;
    public static final DayOfWeek DB_NEW_DAY_OF_WEEK = DayOfWeek.WORKDAY;
    public static final Integer DB_NEW_DEPARTURES_SIZE = 5;
    public static final boolean DB_NEW_ACTIVE = true;


    public static final TransportLine DB_NEW_TL = new TransportLine(100L, "R1", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);

    public static final ArrayList<String> DB_NEW_DEPARTURES = new ArrayList<String>() {
        {
            add("17:00");
            add("17:15");
            add("17:30");
            add("17:45");
            add("18:00");
        }
    };

    public static final Schedule DB_NEW_SCHEDULE = new Schedule(1L, DB_NEW_TL, DB_VALID_DAY_OF_WEEK, DB_NEW_DEPARTURES, true);

    public static final Schedule DB_UPDATE_SCHEDULE = new Schedule(2L, DB_NEW_TL, DB_VALID_DAY_OF_WEEK, DB_NEW_DEPARTURES, true);

    public static final Schedule DB_NULL_SCHEDULE = new Schedule(null, null, null, null, true);

    public static final ArrayList<String> DB_VALID_TL_DEPARTURES = new ArrayList<String>() {
        {
            add("08:00");
            add("08:15");
            add("08:30");
            add("08:45");
            add("09:00");
        }
    };

    public static final ArrayList<String> DB_VALID_TL_DEPARTURES_SAT = new ArrayList<String>() {
        {
            add("18:00");
            add("18:15");
            add("18:30");
            add("18:45");
            add("19:00");
        }
    };

    public static final ArrayList<String> DB_VALID_TL_DEPARTURES_SUN = new ArrayList<String>() {
        {
            add("22:00");
            add("22:15");
            add("22:30");
            add("22:45");
            add("23:00");
        }
    };

    public static final Long DB_DEL_ID = 101L;
    public static final Long DB_DEL_INVALID_ID = 1001L;

    public static final TransportLine DB_INVALID_TRANSPORT_LINE = new TransportLine(5L, "R55", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);

    public static final Long DB_UPDATE_ID = 102L;
    public static final DayOfWeek DB_UPDATE_DAYOFWEEK = DayOfWeek.SATURDAY;

    public static final TransportLine DB_TRANSPORT_LINE = new TransportLine(1L, "R1", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);

    public static final TransportLine DB_TRANSPORT_LINE_1 = new TransportLine(2L, "R2", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);

    public static final TransportLine DB_TRANSPORT_LINE_2 = new TransportLine(3L, "R3", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);

    public static final TransportLine DB_TRANSPORT_LINE_3 = new TransportLine(4L, "R4", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);

    public static final Schedule DB_SCHEDULE = new Schedule(100L, DB_TRANSPORT_LINE, DayOfWeek.WORKDAY, DB_VALID_TL_DEPARTURES, true);
    public static final Schedule DB_SCHEDULE_1 = new Schedule(103L, DB_TRANSPORT_LINE, DayOfWeek.SATURDAY, DB_VALID_TL_DEPARTURES_SAT, true);
    public static final Schedule DB_SCHEDULE_2 = new Schedule(105L, DB_TRANSPORT_LINE, DayOfWeek.SUNDAY, DB_VALID_TL_DEPARTURES_SUN, true);


    public static final Schedule DB_INVALID_SCHEDULE = new Schedule(3L, DB_TRANSPORT_LINE_2, DayOfWeek.SATURDAY, DB_NEW_DEPARTURES, true);


    public static final List<Schedule> DB_SCHEDULES = new ArrayList<Schedule>() {{
        add(DB_SCHEDULE);
        add(DB_SCHEDULE_1);
        add(DB_SCHEDULE_2);
    }};

}
