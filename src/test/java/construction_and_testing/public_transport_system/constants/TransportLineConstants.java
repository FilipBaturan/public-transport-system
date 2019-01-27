package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.*;
import construction_and_testing.public_transport_system.domain.enums.DayOfWeek;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TransportLineConstants {

    public static final int DB_COUNT = 4;
    public static final Long DB_ID = 1L;
    public static final String DB_NAME = "R1";
    public static final VehicleType DB_TYPE = VehicleType.BUS;
    public static final TransportLinePosition DB_POSITION =
            new TransportLinePosition(1L, "", null, true);
    public static final int DB_SCHEDULE_COUNT = 3;
    public static final int DB_SCHEDULES_COUNT = 7;
    public static final Zone DB_ZONE = new Zone(2L, "Liman", null, true);
    public static final boolean DB_ACTIVE = true;
    public static final Long DB_ID_INVALID = 55L;
    public static final int DB_TICKET_COUNT = 2;

    public static final Long NEW_ID = 77L;
    public static final Long NEW_POSITION_ID = 77L;
    public static final String NEW_NAME = "K5";
    public static final String NEW_NAME_SHORT_LENGTH = "";
    public static final String NEW_NAME_LONG_LENGTH = "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
    public static final String NEW_NAME_MIN_LENGTH = "b";
    public static final String NEW_NAME_MAX_LENGTH = "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
    public static final VehicleType NEW_TYPE = VehicleType.METRO;
    public static final TransportLinePosition NEW_POSITION =
            new TransportLinePosition(null, "", null, true);

    public static final Long DEL_ID = 1L;
    public static final Long DEL_ID_INVALID = 33L;

    public static final Long DEFAULT_ZONE_ID = 1L;

    public static final ArrayList<TransportLine> NEW_TRANSPORT_LINES = new ArrayList<>();
    public static final ArrayList<TransportLine> NEW_TRANSPORT_LINES_NO_ZONE = new ArrayList<>();
    public static final ArrayList<TransportLine> NEW_TRANSPORT_LINES_NO_SCHEDULE = new ArrayList<>();
    public static final ArrayList<TransportLine> NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION = new ArrayList<>();
    public static final ArrayList<TransportLine> NEW_TRANSPORT_LINES_INVALID_SCHEDULE = new ArrayList<>();
    public static final ArrayList<TransportLine> NEW_TRANSPORT_LINES_NOT_UNIQUE_NAME = new ArrayList<>();

    public static final int DEL_SCHEDULE_COUNT = 1;

    public static ArrayList<TransportLine> DB_TRANSPORT_LINES = new ArrayList<>();

    public static final String DB_NOT_UNIQUE_NAME = "R1";

    public static final TransportLine DB_TRANSPORT_LINE = new TransportLine(DEL_ID, "R1", VehicleType.BUS,
            new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                    null, true),
            new HashSet<>(), new Zone(2L, "Liman", null, true), true);
    public static TransportLine DEL_TRANSPORT_LINE = DB_TRANSPORT_LINE;

    public static final List<Schedule> DB_SCHEDULES = new ArrayList<>();

    public static final List<Vehicle> DB_VEHICLES = new ArrayList<>();

    public static final List<Ticket> DB_TICKETS = new ArrayList<>();

    static {

        TransportLine transportLine1 =
                new TransportLine(1L, "R1", VehicleType.BUS,
                        new TransportLinePosition(1L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                                null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine1.getPositions().setTransportLine(transportLine1);

        TransportLine transportLine2 =
                new TransportLine(2L, "R2", VehicleType.BUS,
                        new TransportLinePosition(2L, "45.25674,23.45442 46.75338,24.27895(red|R2)",
                                null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine2.getPositions().setTransportLine(transportLine2);

        Schedule schedule1 = new Schedule(100L, transportLine1, DayOfWeek.WORKDAY, new ArrayList<String>() {{
            add("08:00");
            add("08:15");
            add("8:30");
            add("08:45");
            add("09:00");
        }}, true);

        Schedule schedule2 = new Schedule(103L, transportLine1, DayOfWeek.SATURDAY, new ArrayList<String>() {{
            add("18:00");
            add("18:15");
            add("18:30");
            add("18:45");
            add("19:00");
        }}, true);

        Schedule schedule3 = new Schedule(105L, transportLine1, DayOfWeek.SUNDAY, new ArrayList<String>() {{
            add("22:00");
            add("22:15");
            add("22:30");
            add("22:45");
            add("23:00");
        }}, true);

        Schedule schedule4 = new Schedule(101L, transportLine2, DayOfWeek.WORKDAY, new ArrayList<String>() {{
            add("10:00");
            add("10:15");
            add("10:30");
            add("10:45");
            add("11:00");
        }}, true);

        Schedule schedule5 = new Schedule(104L, transportLine2, DayOfWeek.SATURDAY, new ArrayList<String>() {{
            add("15:10");
            add("15:25");
            add("15:40");
            add("15:55");
            add("16:05");
        }}, true);

        Schedule schedule6 = new Schedule(106L, transportLine2, DayOfWeek.SATURDAY, new ArrayList<String>() {{
            add("23:00");
            add("23:15");
            add("23:30");
            add("23:45");
            add("00:00");
        }}, true);

        Ticket ticket1 = new Ticket(1L, LocalDateTime.now(), LocalDateTime.now(), "qweqwe",
                true, new PricelistItem(), transportLine1, new Reservation());
        Ticket ticket2 = new Ticket(2L, LocalDateTime.now(), LocalDateTime.now(), "zxczxczxc",
                true, new PricelistItem(), transportLine2, new Reservation());

        DB_TICKETS.add(ticket1);
        DB_TICKETS.add(ticket2);

        transportLine1.getSchedule().add(schedule1);
        transportLine1.getSchedule().add(schedule2);
        transportLine1.getSchedule().add(schedule3);

        transportLine2.getSchedule().add(schedule4);
        transportLine2.getSchedule().add(schedule5);
        transportLine2.getSchedule().add(schedule6);

        NEW_TRANSPORT_LINES.add(transportLine1);
        NEW_TRANSPORT_LINES.add(transportLine2);


        TransportLine transportLine3 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine3.getPositions().setTransportLine(transportLine3);

        TransportLine transportLine4 =
                new TransportLine(null, "N9", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine4.getPositions().setTransportLine(transportLine4);

        transportLine3.getSchedule().add(new Schedule(schedule1));
        transportLine3.getSchedule().add(new Schedule(schedule2));
        transportLine3.getSchedule().add(new Schedule(schedule3));

        transportLine4.getSchedule().add(new Schedule(schedule4));
        transportLine4.getSchedule().add(new Schedule(schedule5));

        NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION.add(transportLine3);
        NEW_TRANSPORT_LINES_INVALID_SCHEDULE_ASSOCIATION.add(transportLine4);

        TransportLine transportLine5 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), null, true);
        transportLine5.getPositions().setTransportLine(transportLine5);

        TransportLine transportLine6 =
                new TransportLine(null, "N9", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), null, true);
        transportLine6.getPositions().setTransportLine(transportLine6);

        NEW_TRANSPORT_LINES_NO_ZONE.add(transportLine5);
        NEW_TRANSPORT_LINES_NO_ZONE.add(transportLine6);

        TransportLine transportLine7 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        null, new Zone(2L, "Liman", null, true), true);
        transportLine7.getPositions().setTransportLine(transportLine7);

        TransportLine transportLine8 =
                new TransportLine(null, "N9", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        null, new Zone(2L, "Liman", null, true), true);
        transportLine8.getPositions().setTransportLine(transportLine8);

        TransportLine transportLine9 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine9.getPositions().setTransportLine(transportLine9);
        transportLine9.getSchedule().add(new Schedule(55L, transportLine9, DayOfWeek.WORKDAY,
                new ArrayList<>(), true));

        TransportLine transportLine10 =
                new TransportLine(null, "N9", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        null, new Zone(2L, "Liman", null, true), true);
        transportLine10.getPositions().setTransportLine(transportLine10);

        NEW_TRANSPORT_LINES_INVALID_SCHEDULE.add(transportLine9);
        NEW_TRANSPORT_LINES_INVALID_SCHEDULE.add(transportLine10);


        TransportLine transportLine11 =
                new TransportLine(3L, "R3", VehicleType.BUS,
                        new TransportLinePosition(3L, "45.25674,23.45442 46.75338,24.27895(red|R3)",
                                null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine11.getPositions().setTransportLine(transportLine11);

        Schedule schedule7 = new Schedule(102L, transportLine1, DayOfWeek.WORKDAY, new ArrayList<String>() {{
            add("12:00");
            add("12:15");
            add("12:30");
            add("12:45");
            add("13:00");
        }}, true);

        transportLine11.getSchedule().add(schedule7);

        DB_TRANSPORT_LINES.add(transportLine1);
        DB_TRANSPORT_LINES.add(transportLine2);
        DB_TRANSPORT_LINES.add(transportLine11);

        DB_SCHEDULES.add(new Schedule(schedule1));
        DB_SCHEDULES.add(new Schedule(schedule2));
        DB_SCHEDULES.add(new Schedule(schedule3));
        DB_SCHEDULES.add(new Schedule(schedule4));
        DB_SCHEDULES.add(new Schedule(schedule5));
        DB_SCHEDULES.add(new Schedule(schedule6));
        DB_SCHEDULES.add(new Schedule(schedule7));

        Vehicle vehicle1 = new Vehicle(1L, "bus1", VehicleType.BUS, transportLine1, true);
        Vehicle vehicle2 = new Vehicle(2L, "bus2", VehicleType.BUS, transportLine1, true);

        DB_VEHICLES.add(vehicle1);
        DB_VEHICLES.add(vehicle2);


        TransportLine transportLine12 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        null, new Zone(2L, "Liman", null, true), true);
        transportLine12.getPositions().setTransportLine(transportLine12);

        TransportLine transportLine13 =
                new TransportLine(null, "N9", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        null, new Zone(2L, "Liman", null, true), true);
        transportLine13.getPositions().setTransportLine(transportLine13);

        NEW_TRANSPORT_LINES_NO_SCHEDULE.add(transportLine12);
        NEW_TRANSPORT_LINES_NO_SCHEDULE.add(transportLine13);


        TransportLine transportLine14 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine14.getPositions().setTransportLine(transportLine14);

        TransportLine transportLine15 =
                new TransportLine(null, "N7", VehicleType.BUS,
                        new TransportLinePosition(null, "", null, true),
                        new HashSet<>(), new Zone(2L, "Liman", null, true), true);
        transportLine15.getPositions().setTransportLine(transportLine15);

        NEW_TRANSPORT_LINES_NOT_UNIQUE_NAME.add(transportLine14);
        NEW_TRANSPORT_LINES_NOT_UNIQUE_NAME.add(transportLine15);
    }


}
