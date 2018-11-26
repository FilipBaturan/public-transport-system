package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Position;
import construction_and_testing.public_transport_system.domain.TransportLinePosition;

public class TransportLinePositionDTO extends Position {

    public TransportLinePositionDTO() {
    }

    public TransportLinePositionDTO(Long id, double latitude, double longitude, boolean active, Long transportLine) {
        super(id, latitude, longitude, active);
    }

    public TransportLinePositionDTO(TransportLinePosition transportLinePosition){
        this.setId(transportLinePosition.getId());
        this.setLatitude(transportLinePosition.getLatitude());
        this.setLongitude(transportLinePosition.getLongitude());
        this.setActive(transportLinePosition.isActive());
    }
}
