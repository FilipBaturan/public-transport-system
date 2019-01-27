package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.enums.AgeType;
import construction_and_testing.public_transport_system.domain.enums.TicketType;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
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

    @OneToOne(fetch = FetchType.EAGER)
    private Zone zone;

    @Column(nullable = false, name = "active")
    private boolean active;

    @Column(nullable = false, name = "vehicleType")
    private VehicleType vehicleType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item")
    private PricelistItem pricelist;

    public Item() {
        this.active = true;
    }

    public Item(long id, TicketType type, AgeType age, double cost, Zone zone, PricelistItem pricelist) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.cost = cost;
        this.zone = zone;
        this.pricelist = pricelist;
    }

    public Item(long id, TicketType type, AgeType age, double cost, Zone zone, Set<PricelistItem> pricelists, VehicleType vt) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.cost = cost;
        this.zone = zone;
        this.pricelist = pricelist;
        this.vehicleType = vt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
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

    public PricelistItem getPricelist() {
        return pricelist;
    }

    public void setPricelist(PricelistItem pricelist) {
        this.pricelist = pricelist;
        //pricelist.setItem(this);
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

}
