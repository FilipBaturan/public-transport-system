package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket saveTicket(Ticket t) {
        return this.ticketRepository.save(t);
    }

    @Override
    public List<Ticket> findAllTickets() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Ticket findTicketById(long id) {
        return this.ticketRepository.findById(id).get();
    }
}
