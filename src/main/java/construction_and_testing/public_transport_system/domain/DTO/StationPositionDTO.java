package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Position;
import construction_and_testing.public_transport_system.domain.StationPosition;

public class StationPositionDTO extends Position {

    public StationPositionDTO() {
    }

    public StationPositionDTO(Long id, double latitude, double longitude, boolean active, Long station) {
        super(id, latitude, longitude, active);
    }

    public StationPositionDTO(StationPosition stationPosition){
        this.setId(stationPosition.getId());
        this.setLatitude(stationPosition.getLatitude());
        this.setLongitude(stationPosition.getLongitude());
        this.setActive(stationPosition.isActive());
    }
}