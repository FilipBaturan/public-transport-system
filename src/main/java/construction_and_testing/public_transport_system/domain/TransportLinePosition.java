package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.TransportLinePositionDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class TransportLinePosition extends Position {

    @ManyToOne(fetch = FetchType.LAZY)
    private TransportLine transportLine;

    public TransportLinePosition() {
    }

    public TransportLinePosition(Long id, double latitude, double longitude, boolean active, TransportLine transportLine) {
        super(id, latitude, longitude, active);
        this.transportLine = transportLine;
    }

    public TransportLinePosition(TransportLinePositionDTO position, TransportLine transportLine){
        setId(position.getId());
        setLatitude(position.getLatitude());
        setLongitude(position.getLongitude());
        setActive(position.isActive());
        this.transportLine = transportLine;
    }

    public TransportLine getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(TransportLine transportLine) {
        this.transportLine = transportLine;
    }
}
