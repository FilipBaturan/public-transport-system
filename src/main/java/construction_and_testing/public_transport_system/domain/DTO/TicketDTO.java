package construction_and_testing.public_transport_system.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import construction_and_testing.public_transport_system.domain.enums.TicketType;

import java.time.LocalDateTime;

public class TicketDTO {

    private Long id;

    private LocalDateTime purchaseDate;

    private Long line;

    private Long pricelistitem;

    private TicketType ticketType;

    public TicketDTO() {
    }

    public TicketDTO(Long id, LocalDateTime purchaseDate, Long line, Long pricelistitem, TicketType ticketType) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.line = line;
        this.pricelistitem = pricelistitem;
        this.ticketType = ticketType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getLine() {
        return line;
    }

    public void setLine(Long line) {
        this.line = line;
    }

    public Long getPricelistitem() {
        return pricelistitem;
    }

    public void setPricelistitem(Long pricelistitem) {
        this.pricelistitem = pricelistitem;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
