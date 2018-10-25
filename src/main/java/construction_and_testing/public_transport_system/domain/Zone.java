package construction_and_testing.public_transport_system.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Add later
 */
@Entity
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "zone")
    private Set<TransportLine> lines;

    public Zone() {
    }

    public Zone(long id, String name, Set<TransportLine> lines) {
        this.id = id;
        this.name = name;
        this.lines = lines;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
