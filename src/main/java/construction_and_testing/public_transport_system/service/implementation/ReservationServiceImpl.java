package construction_and_testing.public_transport_system.service.implementation;

import construction_and_testing.public_transport_system.repository.ReservationRepository;
import construction_and_testing.public_transport_system.service.definition.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

}
