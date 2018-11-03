package construction_and_testing.public_transport_system.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

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
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "zone")
    private Set<TransportLine> lines;

    @Column(nullable = false, name = "active")
    private boolean active;

    public Zone() {
        this.active = true;
    }

    public Zone(long id, String name, Set<TransportLine> lines) {
        this.id = id;
        this.name = name;
        this.lines = lines;
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
