package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Station;

import java.io.Serializable;

public class StationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private double coordinates;

    private boolean active;

    public StationDTO() {
    }

    public StationDTO(Long id, String name, double coordinates, boolean active) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.active = active;
    }

    public StationDTO(Station station){
        this.id = station.getId();
        this.name = station.getName();
        this.coordinates = station.getCoordinates();
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

    public double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
