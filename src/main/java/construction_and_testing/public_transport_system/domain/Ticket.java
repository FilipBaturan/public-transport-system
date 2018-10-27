package construction_and_testing.public_transport_system.domain;

import construction_and_testing.public_transport_system.domain.util.LocalDateTimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Add later
 */
@Entity
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

    @Column(nullable = false)
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    private Pricelist priceList;

    @OneToOne(fetch = FetchType.LAZY)
    private TransportLine line;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    public Ticket() {
    }

    public Ticket(long id, LocalDateTime purchaseDate, LocalDateTime expiryDate, String token, boolean active,
                  Pricelist priceList, TransportLine line, Reservation reservation) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.token = token;
        this.active = active;
        this.priceList = priceList;
        this.line = line;
        this.reservation = reservation;
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

    public Pricelist getPriceList() {
        return priceList;
    }

    public void setPriceList(Pricelist priceList) {
        this.priceList = priceList;
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
}
