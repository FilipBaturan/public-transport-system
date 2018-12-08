package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.Ticket;

import java.time.LocalDateTime;

public class TicketReportDTO {

    private Long id;
    private boolean active;
    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public void setId(Long id) {
        this.id = id;
    }

    public TicketReportDTO(Ticket t) {

        this.id = t.getId();
        this.active = t.isActive();
        this.purchaseDate = t.getPurchaseDate();
        this.expiryDate = t.getExpiryDate();
    }

    public TicketReportDTO() {

    }
}
