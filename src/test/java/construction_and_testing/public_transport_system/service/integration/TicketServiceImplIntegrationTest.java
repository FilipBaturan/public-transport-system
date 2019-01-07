package construction_and_testing.public_transport_system.service.integration;


import construction_and_testing.public_transport_system.domain.Ticket;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static construction_and_testing.public_transport_system.constants.TicketConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketServiceImplIntegrationTest {


    @Autowired
    TicketService ticketService;

    /**
     * Test get all tickets from database
     */
    @Test
    public void getAll() {
        List<Ticket> tickets = ticketService.findAllTickets();
        assertThat(tickets).hasSize(DB_COUNT);
    }

    /**
     * Test with valid id
     */
    @Test
    public void findByIdValid() {
        Ticket dbTicket = ticketService.findTicketById(DB_ID);
        assertThat(dbTicket).isNotNull();

        assertThat(dbTicket.getId()).isEqualTo(DB_ID);
        assertThat(dbTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(dbTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(dbTicket.isActive()).isEqualTo(DB_ACTIVE);
    }

    @Test(expected = GeneralException.class)
    public void findByIdInvalid() {
        Ticket dbTicket = ticketService.findTicketById(DB_ID_INVALID);
        assertThat(dbTicket).isNull();
    }

    @Transactional
    @Test
    public void saveChangedValidTest() {
        Ticket t1 = ticketService.findTicketById(DB_ID);
        t1.setPurchaseDate(DB_RANDOM_DATE);
        Ticket savedTicket = ticketService.saveTicket(t1);

        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_RANDOM_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

        List<Ticket> tickets = ticketService.findAllTickets();
        assertThat(tickets.size()).isEqualTo(DB_COUNT);

    }


    @Test
    @Transactional
    public void saveNewValidTest() {
        Ticket ticket = ticketService.findTicketById(DB_ID);


        Ticket t1 = new Ticket(null, DB_PUR_DATE, DB_EXP_DATE, "", true, ticket.getPriceList(), ticket.getLine(), ticket.getReservation());
        Ticket savedTicket = ticketService.saveTicket(t1);

        assertThat(savedTicket.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
        assertThat(savedTicket.getExpiryDate()).isEqualTo(DB_EXP_DATE);
        assertThat(savedTicket.getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(savedTicket.getLine().getId()).isEqualTo(DB_LINE_ID);

        List<Ticket> tickets = ticketService.findAllTickets();
        assertThat(tickets.size()).isEqualTo(DB_COUNT + 1);

    }

    @Transactional
    @Test(expected = GeneralException.class)
    public void saveInvalidLine() {
        Ticket t1 = ticketService.findTicketById(DB_ID);
        t1.getLine().setId(DB_TL_INVALID_ID);
        Ticket savedTicket = ticketService.saveTicket(t1);

    }

    @Transactional
    @Test(expected = GeneralException.class)
    public void saveNullLine() {
        Ticket t1 = ticketService.findTicketById(DB_ID);
        t1.getLine().setId(null);
        ticketService.saveTicket(t1);

    }

    @Test(expected = GeneralException.class)
    public void saveInvalidReservation() {
        Ticket t1 = ticketService.findTicketById(DB_ID);
        t1.getReservation().setId(DB_RES_INVALID_ID);
        ticketService.saveTicket(t1);
    }

    @Test(expected = GeneralException.class)
    public void saveNullReservation() {
        Ticket t1 = ticketService.findTicketById(DB_ID);
        t1.getReservation().setId(null);
        ticketService.saveTicket(t1);
    }

    @Transactional
    @Test
    public void removeValidTest() {
        ticketService.remove(DB_ID);
        Ticket removedTicket = ticketService.findTicketById(DB_ID);
        assertFalse(removedTicket.isActive());
    }

    @Transactional
    @Test(expected = EntityNotFoundException.class)
    public void removeUnknownTest() {
        ticketService.remove(DB_ID_INVALID);
        Ticket removedTicket = ticketService.findTicketById(DB_ID);
        assertFalse(removedTicket.isActive());
    }

    @Transactional
    @Test(expected = EntityNotFoundException.class)
    public void removeNullParameter() {
        ticketService.remove(DB_ID_INVALID);
    }

    @Test
    public void getTicketsForResValid() {
        List<Ticket> usersTickets = ticketService.getTiketsForReservation(DB_RESERVATION_ID);
        assertThat(usersTickets).isNotNull();
        assertThat(usersTickets).hasSize(2);
        assertThat(usersTickets.get(0).getId()).isEqualTo(DB_ID);
        assertThat(usersTickets.get(0).getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
        assertThat(usersTickets.get(1).getReservation().getId()).isEqualTo(DB_RESERVATION_ID);
    }

    @Test
    public void getTicketsForResInvalid() {
        List<Ticket> usersTickets = ticketService.getTiketsForReservation(DB_RES_INVALID_ID);
        assertThat(usersTickets).isEmpty();
        assertThat(usersTickets).hasSize(0);

    }

    @Test()
    public void getTicketsForResNull() {
        List<Ticket> usersTickets = ticketService.getTiketsForReservation(null);
        assertThat(usersTickets).isEmpty();
        assertThat(usersTickets).hasSize(0);

    }

    @Test
    public void getReportValid() {
        LocalDate date1 = LocalDate.of(2015, 12, 21);
        LocalDate date2 = LocalDate.of(2018, 3, 8);

        Map<VehicleType, Integer> prices = ticketService.getReport(date1, date2);
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(300);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(0);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(0);
    }

    @Test
    public void getReportReverseDates() {
        LocalDate date1 = LocalDate.of(2015, 12, 21);
        LocalDate date2 = LocalDate.of(2018, 3, 8);

        Map<VehicleType, Integer> prices = ticketService.getReport(date2, date1);
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(-1);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(-1);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(-1);
    }

    @Test(expected = NullPointerException.class)
    public void getReportNullDates() {
        Map<VehicleType, Integer> prices = ticketService.getReport(null, null);
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(0);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(0);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(0);
    }

    @Test
    public void getVisitsByWeekValid()
    {
        Map<String, Integer> prices = ticketService.getVisitsByWeek(DB_REPORT_START_DATE, DB_REPORT_END_DATE);
        assertThat(prices).isNotEmpty();
        assertThat(prices.size()).isEqualTo(1);
        assertThat(prices.get("2016-12-26T00:00,2016-12-19T00:00")).isEqualTo(2);
    }

    @Test
    public void getVisitsByWeekInvalid()
    {
        Map<String, Integer> prices = ticketService.getVisitsByWeek(DB_REPORT_END_DATE, DB_REPORT_START_DATE);
        assertThat(prices).isEmpty();
        assertThat(prices.size()).isEqualTo(0);
    }

    @Test
    public void getVisitsByMonthValid()
    {
        Map<String, Integer> prices = ticketService.getVisitsByMonth(DB_REPORT_START_DATE, DB_REPORT_END_DATE);
        assertThat(prices).isNotEmpty();
        assertThat(prices.size()).isEqualTo(1);
        assertThat(prices.get("2016-DECEMBER")).isEqualTo(2);
    }

    @Test
    public void getVisitsByMonthInvalid()
    {
        Map<String, Integer> prices = ticketService.getVisitsByMonth(DB_REPORT_END_DATE, DB_REPORT_START_DATE);
        assertThat(prices).isEmpty();
        assertThat(prices.size()).isEqualTo(0);
    }

}
