package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.TicketType;
import construction_and_testing.public_transport_system.repository.ReservationRepository;
import construction_and_testing.public_transport_system.service.definition.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getReservationsForUser(Long id) {
        return reservationRepository.getUsersReservation(id);
    }

    @Override
    public boolean reserve(Reservation reservation, List<TicketType> types) {
        makeTickets(reservation.getTickets(), types);
        Reservation saved = reservationRepository.save(reservation);
        if (saved != null) {
            return true;
        }
        return false;
    }

    private void makeTickets(Set<Ticket> tickets, List<TicketType> types) {
        int index = 0;
        for (Ticket t : tickets) {
            TicketType type = types.get(index);
            if (type.equals(TicketType.ONETIME)) {
                t.setExpiryDate(t.getPurchaseDate());
                System.out.println(t.getExpiryDate());
                System.out.println(t.getPurchaseDate());
            } else if (type.equals(TicketType.DAILY)) {
                t.setExpiryDate(t.getPurchaseDate().plusDays(1));
            } else if (type.equals(TicketType.MONTHLY)) {
                t.setExpiryDate(t.getPurchaseDate().plusMonths(1));
            } else {
                t.setExpiryDate(t.getPurchaseDate().plusYears(1));
            }
            index++;
        }
    }
}
