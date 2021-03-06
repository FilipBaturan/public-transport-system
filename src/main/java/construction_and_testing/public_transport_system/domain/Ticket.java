package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.util.LocalDateTimeConverter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Add later
 */
@Entity
@Where(clause = "active =1")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, name = "active")
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    private PricelistItem priceListItem;

    @OneToOne(fetch = FetchType.LAZY)
    private TransportLine line;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    public Ticket() {
    }

    public Ticket(Long id, LocalDateTime purchaseDate, LocalDateTime expiryDate, String token, boolean active,
                  PricelistItem priceList, TransportLine line, Reservation reservation) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.token = token;
        this.active = active;
        this.priceListItem = priceList;
        this.line = line;
        this.reservation = reservation;
    }

    public Ticket(Ticket ticket) {
        this.id = ticket.getId();
        this.purchaseDate = ticket.getPurchaseDate();
        this.expiryDate = ticket.getExpiryDate();
        this.active = ticket.isActive();
        this.priceListItem = ticket.getPriceList();
        this.reservation = ticket.getReservation();
        this.line = new TransportLine(ticket.getLine());
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PricelistItem getPriceList() {
        return priceListItem;
    }

    public void setPriceList(PricelistItem priceList) {
        this.priceListItem = priceList;
    }

    public TransportLine getLine() {
        return line;
    }

    public void setLine(TransportLine line) {
        this.line = line;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return active == ticket.active &&
                Objects.equals(id, ticket.id) &&
                Objects.equals(purchaseDate, ticket.purchaseDate) &&
                Objects.equals(expiryDate, ticket.expiryDate) &&
                Objects.equals(token, ticket.token) &&
                Objects.equals(priceListItem, ticket.priceListItem) &&
                Objects.equals(line, ticket.line) &&
                Objects.equals(reservation, ticket.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
