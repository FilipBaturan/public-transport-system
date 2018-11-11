package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.StationDTO;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Where(clause = "active =1")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private double coordinates;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Station(){
        this.active = true;
    }

    public Station(long id, String name, double coordinates, TransportLine lines, boolean active) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.active = active;
    }

    public Station(StationDTO station){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
