package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.Zone;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.util.ArrayList;
import java.util.HashSet;

public class VehicleConstants {

    public static final int DB_COUNT = 2;
    public static final Long DB_ID = 1L;
    public static final String DB_NAME = "bus1";
    public static final VehicleType DB_TYPE = VehicleType.BUS;
    public static final Long DB_LINE = 1L;
    public static final boolean DB_ACTIVE = true;
    public static final Long DB_ID_INVALID = 55L;

    public static final Long NEW_ID = 7L;
    public static final String NEW_NAME = "bus7";
    public static final String NEW_NAME_SHORT_LENGTH = "";
    public static final String NEW_NAME_LONG_LENGTH = "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
    public static final String NEW_NAME_MIN_LENGTH = "bus";
    public static final String NEW_NAME_MAX_LENGTH = "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
    public static final VehicleType NEW_TYPE = VehicleType.BUS;
    public static final TransportLine NEW_LINE =
            new TransportLine(1L, "R1", VehicleType.BUS, new TransportLinePosition(),
                    new HashSet<>(), new Zone(2L), true);
    public static final VehicleType NEW_TYPE_INVALID = VehicleType.METRO;
    public static final TransportLine NEW_LINE_INVALID = new TransportLine(77L);
    public static final Long NEW_LINE_INVALID_ID = 77L;

    public static final Long DEL_ID = 1L;
    public static final Long DEL_ID_INVALID = 33L;

    public static final Long DB_TR_ID = 1L;
    public static final TransportLine DB_TR =
            new TransportLine(1L, "R1", VehicleType.BUS, new TransportLinePosition(),
                    new HashSet<>(), new Zone(2L), true);

    public static final ArrayList<Vehicle> DB_VEHICLES = new ArrayList<Vehicle>() {{
        add(new Vehicle(DB_ID, DB_NAME, DB_TYPE, DB_TR, DB_ACTIVE));
        add(new Vehicle(2L,  DB_NAME, DB_TYPE, DB_TR, DB_ACTIVE));
    }};
    public static final Vehicle DB_VEHICLE = new Vehicle(DEL_ID, DB_NAME, DB_TYPE, DB_TR, DB_ACTIVE);
    public static Vehicle DEL_VEHICLE = DB_VEHICLE;
}
