package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {


    @Query(value = "SELECT * FROM ticket t WHERE t.reservation_id = ?1", nativeQuery = true)
    List<Ticket> getTiketsForReservation(Long id);

    @Query(value = "SELECT SUM(i.cost) FROM ticket t, pricelist_item pli, item i WHERE t.price_list_item_id = pli.id AND pli.item_id = i.id AND i.vehicle_type = ?3 AND t.purchase_date BETWEEN ?1 AND ?2", nativeQuery = true)
    Long getPrice(LocalDate date1, LocalDate date2, int i);


    @Query(value = "SELECT * FROM ticket t WHERE t.purchase_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Ticket> getTicketsBetween(LocalDate date1, LocalDate date2);

    @Query(value = "SELECT t from ticket t WHERE t.line.id IN :lines", nativeQuery = true)
    List<Ticket> findByTransportLine(@Param("lines") List<Long> transportLineIds);

}
