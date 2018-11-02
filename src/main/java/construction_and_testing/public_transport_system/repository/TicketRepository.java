package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
