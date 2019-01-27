package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ZoneConstants {

    public static final int DB_COUNT = 6;

    public static final Long DB_ID = 2L;
    public static final String DB_NAME = "Liman";
    public static final boolean DB_ACTIVE = true;
    public static final int DB_TR_COUNT = 4;
    public static final Long DB_ID_INVALID = 33L;
    public static final ArrayList<Long> DB_TR_ID = new ArrayList<Long>() {{
        add(21L);
        add(22L);
        add(23L);
    }};
    public static final ArrayList<TransportLine> DB_TR = new
            ArrayList<TransportLine>() {{
                add(new TransportLine(21L, "T1", VehicleType.METRO,
                        new TransportLinePosition(), new HashSet<>(), null, true));
                add(new TransportLine(22L, "T2", VehicleType.TRAM,
                        new TransportLinePosition(), new HashSet<>(), null, true));
                add(new TransportLine(23L, "T3", VehicleType.BUS,
                        new TransportLinePosition(), new HashSet<>(), null, true));
            }};
    public static final HashSet<TransportLine> DB_TR_SAT = new HashSet<>(DB_TR);

    public static final ArrayList<Zone> DB_ZONES = new ArrayList<Zone>() {{
        add(new Zone(DB_ID, DEL_NAME, DB_TR_SAT, true));
        add(new Zone());
        add(new Zone());
    }};


    public static final Long NEW_ID = 55L;
    public static final String NEW_NAME = "Klisa";
    public static final String NEW_NAME_SHORT_LENGTH = "";
    public static final String NEW_NAME_LONG_LENGTH = "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
    public static final String NEW_NAME_MIN_LENGTH = "b";
    public static final String NEW_NAME_MAX_LENGTH = "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
    public static final HashSet<TransportLine> NEW_LINES = new HashSet<>(
            Arrays.asList(new TransportLine(2L), new TransportLine(3L))
    );
    public static final HashSet<TransportLine> NEW_LINES_INVALID = new HashSet<>(
            Arrays.asList(
                    new TransportLine(null, "L5", VehicleType.METRO,
                            new TransportLinePosition(), new HashSet<>(), null, true),
                    new TransportLine(null, "L7", VehicleType.METRO,
                            new TransportLinePosition(), new HashSet<>(), null, true))
    );

    public static final ArrayList<Long> TR_ID_INVALID = new ArrayList<Long>() {{
        add(31L);
        add(32L);
    }};
    public static final HashSet<TransportLine> TR_INVALID = new HashSet<>(
            Arrays.asList(
                    new TransportLine(31L, "D5", VehicleType.METRO,
                            new TransportLinePosition(), new HashSet<>(), null, true),
                    new TransportLine(32L, "D7", VehicleType.METRO,
                            new TransportLinePosition(), new HashSet<>(), null, true))
    );

    public static final Long DEL_ID = 2L;
    public static final Long DEL_ID_INVALID = 77L;
    public static final String DEL_NAME = "Futog";

    public static final Long DEFAULT_ZONE_ID = 1L;
    public static final String NOT_UNIQUE_NAME = "Kamenica";

    public static final Zone DB_ZONE = new Zone(DB_ID, DEL_NAME, new HashSet<>(Arrays.asList(
            new TransportLine(81L, "N5", VehicleType.METRO,
                    new TransportLinePosition(), new HashSet<>(), null, true),
            new TransportLine(82L, "N7", VehicleType.METRO,
                    new TransportLinePosition(), new HashSet<>(), null, true))), DB_ACTIVE);
    public static Zone DEL_ZONE = DB_ZONE;

}
