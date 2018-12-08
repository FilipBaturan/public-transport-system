package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Reservation;

import java.util.List;

public interface ReservationService {

    /**
     *
     * @param id of the user
     * @returns listOfUsersReservation
     */
    List<Reservation> getReservationsForUser(Long id);

}
