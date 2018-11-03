package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private VehicleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private TransportLine currentLine;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Vehicle() {
        this.active = true;
    }

    public Vehicle(long id, String name, VehicleType type, TransportLine currentLine) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.currentLine = currentLine;
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

    public TransportLine getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(TransportLine currentLine) {
        this.currentLine = currentLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
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
