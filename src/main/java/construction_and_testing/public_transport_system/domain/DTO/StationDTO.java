package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Station;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.io.Serializable;

public class StationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private StationPositionDTO position;

    private VehicleType type;

    private boolean active;

    public StationDTO() {
    }

    public StationDTO(Long id, String name, StationPositionDTO position, VehicleType type, boolean active) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.type = type;
        this.active = active;
    }

    public StationDTO(Station station){
        this.id = station.getId();
        this.name = station.getName();
        this.position = new StationPositionDTO(station.getPosition());
        this.type = station.getType();
        this.active = station.isActive();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public StationPositionDTO getPosition() {
        return position;
    }

    public void setPosition(StationPositionDTO position) {
        this.position = position;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}
