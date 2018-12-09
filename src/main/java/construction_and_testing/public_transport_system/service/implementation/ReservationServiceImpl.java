package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.repository.ReservationRepository;
import construction_and_testing.public_transport_system.service.definition.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getReservationsForUser(Long id) {
        return reservationRepository.getUsersReservation(id);
    }
}
