package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.io.Serializable;

public class VehicleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private VehicleType type;

    private Long currentLine;

    private boolean active;

    public VehicleDTO() {
        this.active = true;
    }

    public VehicleDTO(Long id, String name, VehicleType type, Long currentLine, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.currentLine = currentLine;
        this.active = active;
    }

    public VehicleDTO(Vehicle vehicle){
        this.id = vehicle.getId();
        this.name = vehicle.getName();
        this.type = vehicle.getType();
        this.currentLine = vehicle.getCurrentLine().getId();
        this.active = vehicle.isActive();
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

    public Long getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(Long currentLine) {
        this.currentLine = currentLine;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}