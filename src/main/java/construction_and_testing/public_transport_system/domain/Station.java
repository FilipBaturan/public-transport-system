package construction_and_testing.public_transport_system.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private double coordinates;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TransportLine> lines;

    public Station(){

    }

    public Station(long id, String name, double coordinates, Set<TransportLine> lines) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
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

    public double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinates) {
        this.coordinates = coordinates;
    }

    public Set<TransportLine> getLines() {
        return lines;
    }

    public void setLines(Set<TransportLine> lines) {
        this.lines = lines;
    }
}
