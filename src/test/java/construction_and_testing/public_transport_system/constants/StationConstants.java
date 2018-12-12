package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.StationPosition;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class StationConstants {

    public static final int DB_COUNT = 2;
    public static final Long DB_ID = 1L;
    public static final String DB_NAME = "Liman1";
    public static final VehicleType DB_TYPE = VehicleType.BUS;
    public static final Long DB_POSITION = 1L;
    public static final boolean DB_ACTIVE = true;
    public static final Long DB_ID_INVALID = 55L;

    public static final Long NEW_ID = 77L;
    public static final String NEW_NAME = "Kamenica1";
    public static final VehicleType NEW_TYPE = VehicleType.METRO;
    public static final StationPosition NEW_POSITION =
            new StationPosition(null, 45.24, 26.74, true, null);
    public static final boolean NEW_ACTIVE = true;

    public static final Long DEL_ID = 1L;
    public static final Long DEL_ID_INVALID = 33L;

    public static final List<Station> NEW_STATIONS = new ArrayList<Station>() {{
        add(new Station(null, "Sajmiste1", new StationPosition(), VehicleType.TRAM, true));
        add(new Station(null, "Sajmiste2", new StationPosition(), VehicleType.METRO, true));
    }};

    public static List<Station> DB_STATIONS = new ArrayList<Station>() {{
        add(new Station());
        add(new Station());
    }};
    public static final Station DB_STATION = new Station(DB_ID, DB_NAME, new StationPosition(), DB_TYPE, DB_ACTIVE);
    public static Station DEL_STATION = DB_STATION;

}
