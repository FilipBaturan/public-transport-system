package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.domain.Ticket;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query( value = "SELECT * FROM Ticket t WHERE t.active = true AND t.reservation_id = ?1", nativeQuery = true)
    List<Ticket> getTiketsForReservation(Long id);

    @Query(value = "SELECT SUM(i.cost) FROM Item i WHERE i.vehicle_type = ?3 AND i.id IN (SELECT t.price_list_item_id FROM Ticket t WHERE t.active = true AND t.purchase_date BETWEEN ?1 AND ?2)", nativeQuery = true)
    Long getPrice(LocalDate date1, LocalDate date2, int i);
}
