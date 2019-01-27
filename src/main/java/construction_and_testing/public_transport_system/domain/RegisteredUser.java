package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;

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

    @Column
    private String image;

    public RegisteredUser() {
        balance = 0.0;
    }

    public RegisteredUser(Long id, String name, String lastName, String username, String password, String email,

                          String telephone, UsersDocumentsStatus confirmation, Set<Reservation> reservations,
                          double balance, String document) {


        super(id, name, lastName, username, password, email, telephone, confirmation);
        this.reservations = reservations;
        this.balance = balance;
        this.document = document;
        this.setActive(true);
        this.image = "";
        //this.image = image;
    }

    public RegisteredUser(Long id, String name, String lastName, String username, String password, String email,

                          String telephone, UsersDocumentsStatus confirmation, Set<Reservation> reservations,
                          double balance, String document, boolean active) {


        super(id, name, lastName, username, password, email, telephone, confirmation);
        this.reservations = reservations;
        this.balance = balance;
        this.document = document;
        this.setActive(active);
        this.image = "";
        //this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
