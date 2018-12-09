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
    public static final int DB_TR_COUNT = 3;
    public static final ArrayList<Long> DB_TR_ID = new ArrayList<Long>() {{
        add(21L);
        add(22L);
        add(23L);
    }};
    public static final ArrayList<TransportLine> DB_TR = new
            ArrayList<TransportLine>() {{
                add(new TransportLine(21L, "T1", VehicleType.METRO,
                        new TransportLinePosition(), new HashSet<>(), null, true));
                add(new TransportLine(22L, "T2", VehicleType.TRAMVAJ,
                        new TransportLinePosition(), new HashSet<>(), null, true));
                add(new TransportLine(23L, "T3", VehicleType.BUS,
                        new TransportLinePosition(), new HashSet<>(), null, true));
            }};
    public static final HashSet<TransportLine> DB_TR_SAT = new HashSet<>(DB_TR);
    public static final Zone DB_ZONE = new Zone(DB_ID, DB_NAME, new HashSet<>(), DB_ACTIVE);
    public static final ArrayList<Zone> DB_ZONES = new ArrayList<Zone>() {{
        add(new Zone());
        add(new Zone());
        add(new Zone());
    }};

    public static final String NEW_NAME = "Klisa";
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

    public static final Long DEL_ID = 2L;
    public static final Long DEL_ID_INVALID = 77L;

    public static final Long DEFAULT_ZONE_ID = 1L;

}
