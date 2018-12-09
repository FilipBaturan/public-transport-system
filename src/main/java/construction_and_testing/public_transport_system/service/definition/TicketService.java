package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import org.joda.time.LocalDateTime;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TicketService {

    Ticket saveTicket(Ticket t);

    void remove(Long id);

    List<Ticket> findAllTickets();

    Ticket findTicketById(long id);

    List<Ticket> getTiketsForReservation(Long id);

    Map<VehicleType, Integer> getReport(LocalDate date1, LocalDate date2);
}
