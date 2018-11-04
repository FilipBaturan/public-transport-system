package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Ticket;

import java.util.List;

public interface TicketService {

    Ticket saveTicket(Ticket t);

    List<Ticket> findAllTickets();

    Ticket findTicketById(long id);

}
