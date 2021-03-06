package construction_and_testing.public_transport_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineDTO;
import construction_and_testing.public_transport_system.domain.DTO.VehicleTransportLineDTO;
import construction_and_testing.public_transport_system.domain.DTO.ZoneTransportLineDTO;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
public class TransportLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 30)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private VehicleType type;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REFRESH},
            mappedBy = "transportLine")
    private TransportLinePosition positions;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "transportLine")
    private Set<Schedule> schedule;

    @ManyToOne(optional = false)
    private Zone zone;

    @Column(nullable = false, name = "active")
    private boolean active;

    public TransportLine() {
        this.active = true;
    }

    public TransportLine(long id) {
        this.id = id;
        this.active = true;
    }

    public TransportLine(Long id, String name, VehicleType type, TransportLinePosition positions,
                         Set<Schedule> schedule, Zone zone, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.positions = positions;
        this.schedule = schedule;
        this.zone = zone;
        this.active = active;
    }

    public TransportLine(TransportLineDTO transportLine) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        try {
            this.schedule = transportLine.getSchedule().stream().map((Long s) -> new Schedule(s, this))
                    .collect(Collectors.toSet());
        } catch (NullPointerException e) {
            this.schedule = null;
        }
        this.positions = new TransportLinePosition(transportLine.getPositions(), this);
        this.zone = new Zone(transportLine.getZone());
        this.active = transportLine.isActive();
    }

    public TransportLine(TransportLineDTO transportLine, Zone zone) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.schedule = transportLine.getSchedule().stream().map((Long s) -> new Schedule(s, this))
                .collect(Collectors.toSet());
        this.positions = new TransportLinePosition(transportLine.getPositions(), this);
        this.zone = zone;
        this.active = transportLine.isActive();
    }

    public TransportLine(ZoneTransportLineDTO transportLine, Zone zone) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.zone = zone;
    }

    public TransportLine(TransportLine transportLine) {
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.positions = transportLine.getPositions();
        this.schedule = transportLine.getSchedule();
        this.zone = transportLine.getZone();
        this.active = transportLine.isActive();
    }

    public TransportLine(VehicleTransportLineDTO vehicle) {
        this.id = vehicle.getId();
        this.name = vehicle.getName();
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

    public Set<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportLine)) return false;
        TransportLine that = (TransportLine) o;
        return Objects.equals(id, that.id);
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

    public TransportLinePosition getPositions() {
        return positions;
    }

    public void setPositions(TransportLinePosition positions) {

        if (sameAs(positions)) {
            return;
        }
        TransportLinePosition oldPosition = this.positions;
        this.positions = positions;
        if (oldPosition != null) {
            oldPosition.setTransportLine(null);
        }
        if (positions != null) {
            positions.setTransportLine(this);
        }
    }

    private boolean sameAs(TransportLinePosition newPositions) {
        if (this.positions == null) {
            return newPositions == null;
        } else {
            return positions.equals(newPositions);
        }
    }
}
