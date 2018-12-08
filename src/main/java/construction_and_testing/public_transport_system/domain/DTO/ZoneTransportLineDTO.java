package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.io.Serializable;

public class ZoneTransportLineDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private VehicleType type;

    private boolean active;

    public ZoneTransportLineDTO() {
    }

    public ZoneTransportLineDTO(Long id, String name, VehicleType type, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.active = active;
    }

    public ZoneTransportLineDTO(TransportLine transportLine) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
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

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
