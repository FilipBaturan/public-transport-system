package construction_and_testing.public_transport_system.domain.DTO;

import java.util.ArrayList;
import java.util.List;

public class ReservationDTO {

    private long id;

    private long owner;

    private List<TicketDTO> tickets;

    public ReservationDTO() {
    }

    public ReservationDTO(long id, long owner, List<TicketDTO> tickets) {
        this.id = id;
        this.owner = owner;
        this.tickets = tickets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<TicketDTO> tickets) {
        this.tickets = tickets;
    }
}
