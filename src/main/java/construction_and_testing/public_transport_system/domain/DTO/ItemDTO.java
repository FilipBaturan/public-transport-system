package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.enums.AgeType;
import construction_and_testing.public_transport_system.domain.enums.TicketType;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

public class ItemDTO {

    private Long id;

    private TicketType type;

    private AgeType age;

    private double cost;

    private VehicleType vehicleType;

    private Long zone;

    public ItemDTO() {
    }

    public ItemDTO(Long id, TicketType type, AgeType age, double cost, VehicleType vehicleType, Long zone) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.cost = cost;
        this.vehicleType = vehicleType;
        this.zone = zone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public AgeType getAge() {
        return age;
    }

    public void setAge(AgeType age) {
        this.age = age;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }
}
