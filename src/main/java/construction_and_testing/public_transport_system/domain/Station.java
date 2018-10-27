package construction_and_testing.public_transport_system.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private double coordinates;


    public Station(){

    }

    public Station(long id, String name, double coordinates, TransportLine lines) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
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

}
