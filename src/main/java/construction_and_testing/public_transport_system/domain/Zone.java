package construction_and_testing.public_transport_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import construction_and_testing.public_transport_system.domain.DTO.ZoneDTO;
import construction_and_testing.public_transport_system.domain.DTO.ZoneTransportLineDTO;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Zone represents group of transport lines
 */
@Entity
@Where(clause = "active =1")
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 30)
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zone", cascade = CascadeType.MERGE)
    private Set<TransportLine> lines;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Zone() {
        this.active = true;
    }

    public Zone(Long id) {
        this.id = id;
        this.active = true;
    }

    public Zone(Long id, String name, Set<TransportLine> lines, boolean active) {
        this.id = id;
        this.name = name;
        this.lines = lines;
        this.active = active;
    }

    public Zone(ZoneDTO zone) {
        this.id = zone.getId();
        this.name = zone.getName();
        this.active = zone.isActive();
        try {
            this.lines = zone.getLines().stream().map((ZoneTransportLineDTO t) -> new TransportLine(t, this)).
                    collect(Collectors.toSet());
        } catch (NullPointerException e) {
            this.lines = null;
        }

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

    public Set<TransportLine> getLines() {
        return lines;
    }

    public void setLines(Set<TransportLine> lines) {
        this.lines = lines;
        this.lines.forEach(transportLine -> transportLine.setZone(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zone)) return false;
        Zone zone = (Zone) o;
        return Objects.equals(id, zone.id);
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
