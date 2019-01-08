package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.repository.ItemRepository;
import construction_and_testing.public_transport_system.repository.ReservationRepository;
import construction_and_testing.public_transport_system.repository.TicketRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.lang.Math.toIntExact;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TransportLineRepository transportLineRepository;

    @Override
    public Ticket saveTicket(Ticket t) {
//
        try {
            t.setReservation(reservationRepository.findById(t.getReservation().getId()).get());
        } catch (NullPointerException | NoSuchElementException e) {
            throw new GeneralException("Invalid reservation data associated!", HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException e3) {
            throw new GeneralException("Id is not type \"Long\"", HttpStatus.BAD_REQUEST);
        }

        try {
            t.setLine(transportLineRepository.findById(t.getLine().getId()).get());
        } catch (NullPointerException | NoSuchElementException e) {
            throw new GeneralException("Invalid transport line data associated!", HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new GeneralException("Id is not type \"Long\"", HttpStatus.BAD_REQUEST);
        }


        return ticketRepository.save(t);


    }

    @Override
    public void remove(Long id) {
        Optional<Ticket> entity = ticketRepository.findById(id);
        if (entity.isPresent()) {
            Ticket ticket = entity.get();
            ticket.setActive(false);
            ticketRepository.save(ticket);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<Ticket> findAllTickets() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Ticket findTicketById(long id) {
        try {
            return ticketRepository.findById(id).orElseThrow(() ->
                    new GeneralException("Requested ticket does not exist!", HttpStatus.BAD_REQUEST));
        } catch (InvalidDataAccessApiUsageException e) { // null id
            throw new GeneralException("Invalid id!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Ticket> getTiketsForReservation(Long id) {
        return ticketRepository.getTiketsForReservation(id);
    }

    @Override
    public Map<VehicleType, Integer> getReport(LocalDate date1, LocalDate date2) {

        Map<VehicleType, Integer> prices = new HashMap<VehicleType, Integer>();
        for (int i = 0; i < 3; i++) {
            prices.put(VehicleType.values()[i], -1);
        }

        // If dates are not valid, return map with values -1
        if (date1.isBefore(date2)) {
            for (int i = 0; i < 3; i++) {
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

    @Override
    public HashMap<String, Integer> getVisitsByWeek(LocalDate date1, LocalDate date2){


    HashMap<String, Integer> totalVisitsPerWeek = new HashMap<String, Integer>();

        List<Ticket> ticketList = ticketRepository.getTicketsBetween(date1, date2);

        for (Ticket selectedTicket: ticketList) {

            String week = generateWeek(selectedTicket.getPurchaseDate());

            // Ako ne postoji ubacuje 1 kao vrednost, inace poveacava vrednost za 1
            totalVisitsPerWeek.merge(week, 1, Integer::sum);
        }

        return totalVisitsPerWeek;
    }

    @Override
    public HashMap<String, Integer> getVisitsByMonth(LocalDate date1, LocalDate date2) {

        HashMap<String, Integer> totalVisitsPerMonth = new HashMap<String, Integer>();

        List<Ticket> ticketList = ticketRepository.getTicketsBetween(date1, date2);

        for (Ticket selectedTicket : ticketList) {
            String week = generateMonth(selectedTicket.getPurchaseDate());

            // Ako ne postoji ubacuje 1 kao vrednost, inace poveacava vrednost za 1
            totalVisitsPerMonth.merge(week, 1, Integer::sum);
        }

        return totalVisitsPerMonth;

    }

    private String generateWeek(LocalDateTime d) {
        LocalDateTime previous = d.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime next = d.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        String retVal = previous.toString() + "," + next.toString();
        return retVal;
    }

    private String generateMonth(LocalDateTime d) {

        String retVal = d.getYear() + "-" +  d.getMonth();
        return retVal;
    }
}
