package construction_and_testing.public_transport_system.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Add later
 */
@MappedSuperclass
@Where(clause = "active =1")
public abstract class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;


    @Column(nullable = false, name = "active")
    private boolean active;

    public Position() {
        this.active = true;
    }

    public Position(Long id, double latitude, double longitude, boolean active) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
