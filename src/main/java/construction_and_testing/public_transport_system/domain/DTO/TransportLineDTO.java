package construction_and_testing.public_transport_system.domain.DTO;

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

    private Set<TransportLinePositionDTO> positions;

    private Set<ScheduleDTO> schedule;

    private boolean active;

    private Long zone;

    private String color;

    private String width;

    public TransportLineDTO() {
        this.color = "blue";
        this.width = "";
        this.active = true;

    }

    public TransportLineDTO(Long id, String name, VehicleType type, Set<TransportLinePositionDTO> positions,
                            Set<ScheduleDTO> schedule, boolean active, Long zone, String color, String width) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.positions = positions;
        this.schedule = schedule;
        this.active = active;
        this.zone = zone;
        this.color = color;
        this.width = width;
    }

    public TransportLineDTO(TransportLine transportLine){
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.active = transportLine.isActive();
        this.color = transportLine.getColor();
        this.width = transportLine.getWidth();
        this.schedule = transportLine.getSchedule().stream().map(ScheduleDTO::new).collect(Collectors.toSet());
        this.positions = transportLine.getPositions().stream().map(TransportLinePositionDTO::new).collect(Collectors.toSet());
        this.zone = transportLine.getZone().getId();
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

    public Set<ScheduleDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<ScheduleDTO> schedule) {
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


    public Set<TransportLinePositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(Set<TransportLinePositionDTO> positions) {
        this.positions = positions;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
