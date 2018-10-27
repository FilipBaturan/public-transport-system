package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.enums.Days;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * Add later
 */
@Entity
public class TransportLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private VehicleType type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Station> stations;

    @Column
    private HashMap<Days,ArrayList<LocalDateTime>> schedule;

    @ManyToOne(optional = false)
    private Zone zone;

    public TransportLine() {
    }

    public TransportLine(long id, String name, VehicleType type, Set<Station> stations, HashMap<Days,
            ArrayList<LocalDateTime>> schedule, Zone zone) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stations = stations;
        this.schedule = schedule;
        this.zone = zone;
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

    public Set<Station> getStations() {
        return stations;
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    public HashMap<Days, ArrayList<LocalDateTime>> getSchedule() {
        return schedule;
    }

    public void setSchedule(HashMap<Days, ArrayList<LocalDateTime>> schedule) {
        this.schedule = schedule;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
