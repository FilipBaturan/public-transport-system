package construction_and_testing.public_transport_system.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Add later
 */
@Entity
@DiscriminatorValue("REGISTERED_USER")
public class RegisteredUser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Reservation> reservations;

    @Column
    private double balance;

    @Column
    private String document;

    public RegisteredUser() {
        balance = 0.0;
    }

    public RegisteredUser(Long id, String name, String lastName, String username, String password, String email,
                          String telephone, boolean confirmation, Set<Reservation> reservations, double balance, String document) {
        super(id, name, lastName, username, password, email, telephone, confirmation);
        this.reservations = reservations;
        this.balance = balance;
        this.document = document;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
