package construction_and_testing.public_transport_system.util;

import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.io.Serializable;
import java.util.List;

public class TrackedVehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private VehicleType vehicleType;

    private double latitude;

    private double longitude;

    private boolean active;

    public TrackedVehicle() {
        this.active = true;
    }

    public TrackedVehicle(Long id, String name, VehicleType type, double latitude, double longitude, boolean active) {
        this.id = id;
        this.name = name;
        this.vehicleType = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
    }


    public TrackedVehicle(Vehicle vehicle, List<Double> positions) {
        this.id = vehicle.getId();
        this.name = vehicle.getName();
        this.vehicleType = vehicle.getType();
        this.latitude = positions.get(0);
        this.longitude = positions.get(1);
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
