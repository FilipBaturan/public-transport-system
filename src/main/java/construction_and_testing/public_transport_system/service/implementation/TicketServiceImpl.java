package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.repository.TicketRepository;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket saveTicket(Ticket t) {
        try {
            return this.ticketRepository.save(t);
        } catch (DataIntegrityViolationException e){
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        Optional<Ticket> entity = ticketRepository.findById(id);
        if(entity.isPresent()){
            Ticket ticket = entity.get();
            ticket.setActive(false);
            ticketRepository.save(ticket);
        }else {
            throw new EntityNotFoundException();
        }
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
