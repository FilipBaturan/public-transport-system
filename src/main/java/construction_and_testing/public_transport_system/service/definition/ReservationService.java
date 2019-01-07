package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.domain.enums.TicketType;

import java.util.List;

public interface ReservationService {

    /**
     * @param id of the user
     * @returns listOfUsersReservation
     */
    List<Reservation> getReservationsForUser(Long id);

    /**
     * @param reservation of user
     * @param types       of tickets, for calculating expiring date
     * @return true if reservation is saved, false if not
     */
    boolean reserve(Reservation reservation, List<TicketType> types);


}
