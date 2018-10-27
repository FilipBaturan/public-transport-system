package construction_and_testing.public_transport_system.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Add later
 */
@Entity
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reservation")
    private Set<Ticket> tickets;

    @ManyToOne(fetch = FetchType.LAZY)
    private RegisteredUser owner;

    public Reservation() {
    }

    public Reservation(long id, Set<Ticket> tickets, RegisteredUser owner) {
        this.id = id;
        this.tickets = tickets;
        this.owner = owner;
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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public RegisteredUser getOwner() {
        return owner;
    }

    public void setOwner(RegisteredUser owner) {
        this.owner = owner;
    }
}
