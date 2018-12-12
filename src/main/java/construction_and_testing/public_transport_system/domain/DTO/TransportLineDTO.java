package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class TransportLineDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private VehicleType type;

    private TransportLinePositionDTO positions;

    private Set<Long> schedule;

    private boolean active;

    private Long zone;

    public TransportLineDTO() {
        this.active = true;

    }

    public TransportLineDTO(Long id, String name, VehicleType type, TransportLinePositionDTO positions,
                            Set<Long> schedule, boolean active, Long zone) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.positions = positions;
        this.schedule = schedule;
        this.active = active;
        this.zone = zone;
    }

    public TransportLineDTO(TransportLine transportLine) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.active = transportLine.isActive();
        try {
            this.schedule = transportLine.getSchedule().stream().map(Schedule::getId).collect(Collectors.toSet());
        } catch (NullPointerException e) {
            this.schedule = null;
        }
        this.positions = new TransportLinePositionDTO(transportLine.getPositions());
        try {
            this.zone = transportLine.getZone().getId();
        } catch (NullPointerException e) {
            this.zone = null;
        }

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

    public Set<Long> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<Long> schedule) {
        this.schedule = schedule;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public TransportLinePositionDTO getPositions() {
        return positions;
    }

    public void setPositions(TransportLinePositionDTO positions) {
        this.positions = positions;
    }
}
