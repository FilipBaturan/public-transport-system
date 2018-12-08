package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Item;
import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.repository.ItemRepository;
import construction_and_testing.public_transport_system.repository.TicketRepository;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import org.hibernate.dialect.Ingres9Dialect;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.toIntExact;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ItemRepository itemRepository;

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

    @Override
    public List<Ticket> getTiketsForReservation(Long id) {
        return ticketRepository.getTiketsForReservation(id);
    }

    @Override
    public Map<VehicleType, Integer> getReport(LocalDate date1, LocalDate date2) {

        Map<VehicleType, Integer> prices = new HashMap<VehicleType, Integer>();
        for(int i = 0; i <  3; i++)
        {
            prices.put(VehicleType.values()[i], -1);
        }

        // If dates are not valid, return map with values -1
        if(date1.isBefore(date2))
        {
            for(int i = 0; i <  3; i++)
            {
                Long databaseCost = ticketRepository.getPrice(date1, date2, i);
                int cost;
                if (databaseCost == null)
                    cost = 0;
                else
                    cost = toIntExact(databaseCost);

                prices.put(VehicleType.values()[i], cost);
            }
        }

        return prices;


    }
}
