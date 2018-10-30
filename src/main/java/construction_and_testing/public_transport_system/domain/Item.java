package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.enums.AgeType;
import construction_and_testing.public_transport_system.domain.enums.TicketType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Add later
 */
@Entity
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TicketType type;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AgeType age;

    @Column(nullable = false)
    private double cost;

    //sta staviti za cascade ????
    @OneToOne(fetch = FetchType.EAGER)
    private Zone zone;

    public Item() {
    }

    public Item(long id, TicketType type, AgeType age, double cost, Zone zone) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.cost = cost;
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

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public AgeType getAge() {
        return age;
    }

    public void setAge(AgeType age) {
        this.age = age;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
