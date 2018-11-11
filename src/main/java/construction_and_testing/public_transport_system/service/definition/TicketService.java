package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Ticket;

import java.util.List;

public interface TicketService {

    Ticket saveTicket(Ticket t);

    void remove(Long id);

    List<Ticket> findAllTickets();

    Ticket findTicketById(long id);

}
