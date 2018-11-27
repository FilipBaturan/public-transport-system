package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.StationPositionDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class StationPosition extends Position {

    @OneToOne(fetch = FetchType.EAGER)
    private Station station;

    public StationPosition() {
    }

    public StationPosition(Long id, double latitude, double longitude, boolean active, Station station) {
        super(id, latitude, longitude, active);
        this.station = station;
    }

    public StationPosition(StationPositionDTO position){
        setId(position.getId());
        setLatitude(position.getLatitude());
        setLongitude(position.getLongitude());
        setActive(position.isActive());
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
