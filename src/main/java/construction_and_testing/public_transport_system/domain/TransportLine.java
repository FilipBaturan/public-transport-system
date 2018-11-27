package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.DTO.ScheduleDTO;
import construction_and_testing.public_transport_system.domain.DTO.TransportLineDTO;
import construction_and_testing.public_transport_system.domain.DTO.TransportLinePositionDTO;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private VehicleType type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transportLine")
    private Set<TransportLinePosition> positions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transportLine")
    private Set<Schedule> schedule;

    @ManyToOne(optional = false)
    private Zone zone;

    @Column
    private String color;

    @Column
    private String width;

    @Column(nullable = false, name = "active")
    private boolean active;

    public TransportLine() {
        this.color = "blue";
        this.width = "";
        this.active = true;
    }

    public TransportLine(long id){
        this.id = id;
        this.color = "blue";
        this.width = "";
        this.active = true;
    }

    public TransportLine(long id, String name, VehicleType type, Set<TransportLinePosition> positions,
                         Set<Schedule> schedule, Zone zone, String color, String width, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.positions = positions;
        this.schedule = schedule;
        this.zone = zone;
        this.color = color;
        this.width = width;
        this.active = active;
    }

    public TransportLine(TransportLineDTO transportLine){
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.color = transportLine.getColor();
        this.width = transportLine.getWidth();
        this.schedule = transportLine.getSchedule().stream().map((ScheduleDTO s) -> new Schedule(s,this))
                .collect(Collectors.toSet());
        this.positions = transportLine.getPositions().stream().map(
                (TransportLinePositionDTO t) -> new TransportLinePosition(t,this))
                .collect(Collectors.toSet());
        this.zone = new Zone(transportLine.getZone());
        this.active = transportLine.isActive();
    }

    public TransportLine(TransportLineDTO transportLine, Zone zone){
        this.id = transportLine.getId();
        this.name = transportLine.getName();
        this.type = transportLine.getType();
        this.color = transportLine.getColor();
        this.width = transportLine.getWidth();
        this.schedule = transportLine.getSchedule().stream().map((ScheduleDTO s) -> new Schedule(s,this))
                .collect(Collectors.toSet());
        this.positions = transportLine.getPositions().stream().map(
                (TransportLinePositionDTO t) -> new TransportLinePosition(t,this))
                .collect(Collectors.toSet());
        this.zone = zone;
        this.active = transportLine.isActive();
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

    public Set<TransportLinePosition> getPositions() {
        return positions;
    }

    public void setPositions(Set<TransportLinePosition> positions) {
        this.positions = positions;
    }
}
