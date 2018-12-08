package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.StationDTO;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "station")
    private StationPosition position;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private VehicleType type;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Station(){
        this.active = true;
    }

    public Station(long id){
        this.id = id;
        this.active = true;
    }

    public Station(long id, String name, StationPosition position, VehicleType type, boolean active) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.type = type;
        this.active = active;
    }

    public Station(StationDTO station){
        this.id = station.getId();
        this.name = station.getName();
        this.position = new StationPosition(station.getPosition(), this);
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

    public StationPosition getPosition() {
        return position;
    }

    public void setPosition(StationPosition position) {
        this.position = position;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}
